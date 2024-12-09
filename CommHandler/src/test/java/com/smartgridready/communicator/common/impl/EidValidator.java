package com.smartgridready.communicator.common.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.smartgridready.communicator.common.helper.XmlResourceLoader;
import com.smartgridready.ns.v0.DeviceFrame;
import com.smartgridready.ns.v0.FunctionalProfileFrame;

/**
 * A simple tool that validates the EIDs in SGrSpecifications against the
 * declared functional profiles in the same repository.
 */
public class EidValidator {

    private static final String XML_BASE_DIR = "../SGrSpecifications/XMLInstances";
    private static final String FP_DIR = "FuncProfiles";
    private static final String EID_DIR = "ExtInterfaces";

    private static final String FILE_SUFFIX = ".xml";

    /**
     * Runs the tool.
     * @param args the command-line arguments.
     */
    public static void main(String[] args) {

        if (!Files.isDirectory(Path.of(XML_BASE_DIR))) {
            System.err.println(String.format("Directory '%s' not available.", XML_BASE_DIR));
            return;
        }

        // load FPs
        final List<FunctionalProfileFrame> fps = loadFunctionalProfiles();
        System.out.println(String.format("Loaded %d FPs.", fps.size()));

        // load EIDs
        final List<DeviceFrame> eids = loadEids();
        System.out.println(String.format("Loaded %d EIDs.", eids.size()));

        // iterate through all EIDs, and then through all FPs
        eids.forEach(eid -> {
            validateEid(eid, fps);
        });
    }

    private static void validateEid(DeviceFrame eid, List<FunctionalProfileFrame> fps) {
        final DeviceWithInterface dev = DeviceWithInterface.of(eid);
        final String devName = eid.getDeviceName();
        
        final List<String> messages = new LinkedList<>();

        dev.getFunctionalProfiles().forEach(eidFP -> {
            final String eidName = eidFP.getFunctionalProfile().getFunctionalProfileName();
            final String eidCategory = eidFP.getFunctionalProfile().getFunctionalProfileIdentification().getFunctionalProfileCategory().value();
            final String eidType = eidFP.getFunctionalProfile().getFunctionalProfileIdentification().getFunctionalProfileType();
            final String eidVersion = eidFP.getFunctionalProfile().getFunctionalProfileIdentification().getVersionNumber().toString();

            final List<FunctionalProfileFrame> fpMatch = fps
                .stream()
                .filter(fp ->
                    eidCategory.equals(fp.getFunctionalProfile().getFunctionalProfileIdentification().getFunctionalProfileCategory().value()) &&
                    eidType.equals(fp.getFunctionalProfile().getFunctionalProfileIdentification().getFunctionalProfileType()) &&
                    eidVersion.equals(fp.getFunctionalProfile().getFunctionalProfileIdentification().getVersionNumber().toString())
                )
                .collect(Collectors.toList());
            if (fpMatch.size() == 0) {
                messages.add(String.format("Validation Error: %s:%s - No matching FP Type %s:%s", devName, eidName, eidCategory, eidType));
                return;
            } else if (fpMatch.size() > 1) {
                messages.add(String.format("Validation Error: %s:%s - Multiple matching FP Type %s:%s", devName, eidName, eidCategory, eidType));
                return;
            }
        });

        messages.forEach(m -> System.out.println(m));
    }

    private static List<FunctionalProfileFrame> loadFunctionalProfiles() {
        final XmlResourceLoader<FunctionalProfileFrame> fpLoader = new XmlResourceLoader<>(FunctionalProfileFrame.class);
        final List<FunctionalProfileFrame> fps = new LinkedList<>();

        try {
            final List<Path> fpPaths = listFiles(Path.of(XML_BASE_DIR, FP_DIR), FILE_SUFFIX);
            fpPaths.forEach(p -> {
                try {
                    FunctionalProfileFrame fp = fpLoader.load("dontcare", loadTextFile(p), false);
                    fps.add(fp);
                } catch (IOException e) {
                    System.err.println(String.format("Failed to load FP '%s': %s", p, e.getMessage()));
                }
            });
        } catch (IOException e) {
            System.err.println(String.format("Failed to load FPs': %s", e.getMessage()));
        }

        return fps;
    }

    private static List<DeviceFrame> loadEids() {
        final XmlResourceLoader<DeviceFrame> eidLoader = new XmlResourceLoader<>(DeviceFrame.class);
        final List<DeviceFrame> eids = new LinkedList<>();

        try {
            final List<Path> fpPaths = listFiles(Path.of(XML_BASE_DIR, EID_DIR), FILE_SUFFIX);
            fpPaths.forEach(p -> {
                try {
                    DeviceFrame fp = eidLoader.load("dontcare", loadTextFile(p), false);
                    eids.add(fp);
                } catch (IOException e) {
                    System.err.println(String.format("Failed to load EID '%s': %s", p, e.getMessage()));
                }
            });
        } catch (IOException e) {
            System.err.println(String.format("Failed to load EIDs': %s", e.getMessage()));
        }

        return eids;
    }

    private static List<Path> listFiles(Path path, String suffix) throws IOException {
        if (!Files.isDirectory(path)) {
            throw new IOException(String.format("Path '%s' is not a directory", path));
        }

        return Files.list(path)
            .filter(p -> Files.isRegularFile(p))
            .filter(p -> p.getFileName().toString().endsWith(suffix))
            .collect(Collectors.toList());
    }

    private static String loadTextFile(Path path) throws IOException {
        return Files.readString(path, StandardCharsets.UTF_8);
    }
}
