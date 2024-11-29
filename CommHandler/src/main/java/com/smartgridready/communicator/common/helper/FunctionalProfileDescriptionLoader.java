package com.smartgridready.communicator.common.helper;
/*
Copyright(c) 2022 Verein SmartGridready Switzerland
@generated NOT

This Open Source Software is BSD 3 clause licensed:
Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in
   the documentation and/or other materials provided with the distribution.
3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from
   this software without specific prior written permission.
THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smartgridready.ns.v0.FunctionalProfileFrame;


public class FunctionalProfileDescriptionLoader {

	private static final Logger LOG = LoggerFactory.getLogger(FunctionalProfileDescriptionLoader.class);

	private final XmlResourceLoader<FunctionalProfileFrame> resourceLoader = new XmlResourceLoader<>(FunctionalProfileFrame.class);

	/**
	 * Load a functional profile description from its FP-XML file.
	 *
	 * @param aBaseDir The path to the folder where the external interface file resides.
	 * @param aDescriptionFile The external interface file name.
	 * @return An instance of the functional profile description for the given FP-XML.
	 */
	public FunctionalProfileFrame load(String aBaseDir, String aDescriptionFile) {	
		try {
			// using java.nio.Path would be better, but absolute paths seem to cause problems on Windows
			String aDescriptionPath = aBaseDir + File.separator + aDescriptionFile;
			
			File fpDescFile = new File(aDescriptionPath);
			String fpDescXml = FileUtils.readFileToString(fpDescFile, StandardCharsets.UTF_8);
			
			return loadFunctionalProfileFrame(aDescriptionPath.toString(), fpDescXml);
		} catch (Exception e) {
			LOG.error("Error loading XML: ", e);
			return null;
		}
	}

	/**
	 * Load a functional profile description from its FP-XML file.
	 *
	 * @param aDescriptionFile The external interface file name.
	 * @param aDescriptionStream The external interface EI-XML input stream.
	 * @return An instance of the functional profile description for the given FP-XML.
	 */
	public FunctionalProfileFrame load(String aDescriptionFile, InputStream aDescriptionStream) {
		try {
			String fpDescXml = new String(aDescriptionStream.readAllBytes(), StandardCharsets.UTF_8);
			return loadFunctionalProfileFrame(aDescriptionFile, fpDescXml);
		} catch (Exception e) {
			LOG.error("Error loading XML: ", e);
			return null;
		}
	}

	/**
	 * Load a functional profile description from its FP-XML file.
	 *
	 * @param fpDescXml The FP-XML file content.
	 * @return An instance of the functional profile description for the given FP-XML.
	 */
	public FunctionalProfileFrame load(String fpDescXml) {	
		try {
			// create random file name
			String aDescriptionPath = UUID.randomUUID().toString() + ".xml";
			return loadFunctionalProfileFrame(aDescriptionPath, fpDescXml);
		} catch (Exception e) {
			LOG.error("Error loading XML: ", e);
			return null;
		}
	}

	private FunctionalProfileFrame loadFunctionalProfileFrame(String resourcePath, String fpDescXml) throws IOException {
		return resourceLoader.load(resourcePath, fpDescXml, false);		
	}
}
