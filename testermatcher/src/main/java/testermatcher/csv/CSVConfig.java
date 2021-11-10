package testermatcher.csv;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public final class CSVConfig {

	private CsvSchema schema;
	private CsvMapper mapper;
	
	public static CSVConfig createDefaultCSVConfig() {
		CsvSchema orderLineSchema = CsvSchema.emptySchema().withHeader().withColumnSeparator(CSVConst.CSV_COLUMN_SEPARATOR)
				.withQuoteChar(CSVConst.CSV_QUOTE_CHAR);
		
		CsvMapper csvMapper = new CsvMapper();
		csvMapper.setDateFormat(new SimpleDateFormat(CSVConst.CSV_DATE_FORMAT));
		csvMapper.enable(CsvParser.Feature.SKIP_EMPTY_LINES);
		
		return new CSVConfig(orderLineSchema, csvMapper);
	}
	
	public CSVConfig(CsvSchema schema, CsvMapper mapper) {
		this.schema = schema;
		this.mapper = mapper;
	}

	public CsvSchema getSchema() {
		return schema;
	}

	public CsvMapper getMapper() {
		return mapper;
	}
}
