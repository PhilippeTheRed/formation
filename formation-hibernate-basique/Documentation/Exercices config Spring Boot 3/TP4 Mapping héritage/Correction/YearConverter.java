package fr.insee.formation.hibernate.model.converter;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class YearConverter implements AttributeConverter<Year, LocalDate> {

	@Override
	public LocalDate convertToDatabaseColumn(Year year) {
		return LocalDate.of(year.getValue(), Month.JANUARY, 1);
	}

	@Override
	public Year convertToEntityAttribute(LocalDate localDate) {
		return Year.from(localDate);
	}

}
