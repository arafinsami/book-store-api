package com.sami.utils;

import java.io.File;
import java.util.logging.Logger;

public class FileDeleteUtils {

	public static void deleteFromDisc(String path, String message) {

		final Logger LOGGER = Logger.getLogger(message.toString());
		try {
			File fileOld = new File(path);
			if (fileOld.delete()) {
				LOGGER.info("****************FILE Deleted From:" + LOGGER.getName());
			} else {
				LOGGER.info("***************Delete operation Is Failed !!!");
			}
		} catch (Exception e) {
			LOGGER.info("*******************Failed to Delete File !!!");
		}
	}
}
