package testermatcher.csv;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.MappingIterator;

public final class CSVReader {

	private static final Logger LOGGER = Logger.getLogger( CSVReader.class.getName() );
	
	public static <T> List<T> readSimpleObjectsList(Class<T> type, String filePath, CSVConfig config) {
		
		try {
			MappingIterator<T> it = config.getMapper().readerFor(type).with(config.getSchema())
					.readValues(new File(filePath));
			return it.readAll();

		} catch (IOException e) {
			e.printStackTrace();
		    LOGGER.log(Level.SEVERE, "Error occurred while reading objects for " + filePath);
		    return null;
		}

	}
}
