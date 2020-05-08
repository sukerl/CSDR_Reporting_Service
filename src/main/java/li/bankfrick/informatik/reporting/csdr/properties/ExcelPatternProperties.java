package li.bankfrick.informatik.reporting.csdr.properties;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

// Lädt Properties im Namespace "technical.excel" aus application.properties in Hashmap
@ConfigurationProperties("technical.excel")
public class ExcelPatternProperties {
	
	private Map<String, String> excelPatterns = new HashMap<>();
	
	// Laden des Namespace "technical.excel.pattern" in Hashmap
    public Map<String, String> getPattern() {
        return excelPatterns;
    }
}

