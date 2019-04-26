package com.bigAlsTexture.SpringBucks.converter;

import org.bson.Document;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.core.convert.converter.Converter;

public class MongoReadConverter implements Converter<Document, Money> {
    @Override
    public Money convert(Document document) {
        Document source = (Document) document.get("money");
        Document currency = (Document) source.get("currency");
        return Money.of(CurrencyUnit.of(currency.get("code").toString()), Double.valueOf(source.get("amount").toString()));
    }
}
