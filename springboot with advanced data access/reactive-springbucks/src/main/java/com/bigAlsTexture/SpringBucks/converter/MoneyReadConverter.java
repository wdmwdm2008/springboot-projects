package com.bigAlsTexture.SpringBucks.converter;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;


@ReadingConverter
public class MoneyReadConverter implements Converter<Long, Money> {

    @Override
    public Money convert(Long aLong) {
        return Money.of(CurrencyUnit.of("CAD"), aLong);
    }
}
