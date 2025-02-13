package fr.insee.formation.hibernate.archi_hex.domain.converter;

import java.time.LocalDate;
import java.time.YearMonth;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class YearMonthConverter implements AttributeConverter<YearMonth, LocalDate> {

	@Override
	public LocalDate convertToDatabaseColumn(YearMonth yearMonth) {
		return LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
	}

	@Override
	public YearMonth convertToEntityAttribute(LocalDate localDate) {
		return YearMonth.from(localDate);
	}
}
