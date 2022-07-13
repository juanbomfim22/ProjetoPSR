package br.ufs.dcomp.projetopsr.domain.converters;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.ufs.dcomp.projetopsr.domain.enums.DiaDaSemana;

@Converter(autoApply = true)
public class DiaDaSemanaConverter implements AttributeConverter<DiaDaSemana, String> {
 
    @Override
    public String convertToDatabaseColumn(DiaDaSemana category) {
        if (category == null) {
            return null;
        }
        return category.getValor();
    }

    @Override
    public DiaDaSemana convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(DiaDaSemana.values())
          .filter(c -> c.getValor().equals(code))
          .findFirst()
          .orElseThrow(IllegalArgumentException::new);
    }
}