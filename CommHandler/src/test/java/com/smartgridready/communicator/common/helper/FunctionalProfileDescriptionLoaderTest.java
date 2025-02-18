package com.smartgridready.communicator.common.helper;

import com.smartgridready.ns.v0.FunctionalProfileFrame;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ConfigurationLoader;
import utils.TestConfiguration;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FunctionalProfileDescriptionLoaderTest {

	private static final Logger LOG = LoggerFactory.getLogger(FunctionalProfileDescriptionLoaderTest.class);

	// Tests do not run on the build pipeline. The FP-XML are not available.
	static boolean isWin() {
		return System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("win");
	}
	
	@Test
	@EnabledIf("isWin")
	void testLoadFunctionalProfileDescriptionsFromFiles() throws Exception {

		TestConfiguration config = new ConfigurationLoader<TestConfiguration>()
				.load("fpdescriptions.yaml", TestConfiguration.class);
		
		String folder = config.getDescriptionFolder();
		Collection<TestConfiguration.Description> files = config.getDescriptions();
		
		files.forEach(desc -> assertDoesNotThrow(() -> {
			LOG.info("Loading file: " + desc.getFile());
			FunctionalProfileDescriptionLoader loader = new FunctionalProfileDescriptionLoader();
			FunctionalProfileFrame fpDesc = loader.load(folder, desc.getFile());
			assertNotNull(fpDesc);
			LOG.info("Loaded FP" + fpDesc.getFunctionalProfile().getFunctionalProfileIdentification().getFunctionalProfileType() + " - "
				+ fpDesc.getFunctionalProfile().getFunctionalProfileIdentification().getFunctionalProfileCategory() + "\n");
		}));
	}

	@Test
	@EnabledIf("isWin")
	void testLoadFunctionalProfileDescriptionsFromStreams() throws Exception {

		TestConfiguration config = new ConfigurationLoader<TestConfiguration>()
				.load("fpdescriptions.yaml", TestConfiguration.class);
		
		String folder = config.getDescriptionFolder();
		Collection<TestConfiguration.Description> files = config.getDescriptions();
		
		files.forEach(desc -> assertDoesNotThrow(() -> {
			LOG.info("Loading file: " + desc.getFile());
			FunctionalProfileDescriptionLoader loader = new FunctionalProfileDescriptionLoader();
			InputStream is = FileUtils.openInputStream(new File(folder + File.separator + desc.getFile()));
			FunctionalProfileFrame fpDesc = loader.load(desc.getFile(), is);
			assertNotNull(fpDesc);
			LOG.info("Loaded FP" + fpDesc.getFunctionalProfile().getFunctionalProfileIdentification().getFunctionalProfileType() + " - "
				+ fpDesc.getFunctionalProfile().getFunctionalProfileIdentification().getFunctionalProfileCategory() + "\n");
		}));
	}

	@Test
	@EnabledIf("isWin")
	void testLoadFunctionalProfileDescriptionsFromStrings() throws Exception {

		TestConfiguration config = new ConfigurationLoader<TestConfiguration>()
				.load("fpdescriptions.yaml", TestConfiguration.class);
		
		String folder = config.getDescriptionFolder();
		Collection<TestConfiguration.Description> files = config.getDescriptions();
		
		files.forEach(desc -> assertDoesNotThrow(() -> {
			LOG.info("Loading file: " + desc.getFile());
			FunctionalProfileDescriptionLoader loader = new FunctionalProfileDescriptionLoader();
			String xml = FileUtils.readFileToString(new File(folder + File.separator + desc.getFile()), StandardCharsets.UTF_8);
			FunctionalProfileFrame fpDesc = loader.load(xml);
			assertNotNull(fpDesc);
			LOG.info("Loaded FP" + fpDesc.getFunctionalProfile().getFunctionalProfileIdentification().getFunctionalProfileType() + " - "
				+ fpDesc.getFunctionalProfile().getFunctionalProfileIdentification().getFunctionalProfileCategory() + "\n");
		}));
	}
}
