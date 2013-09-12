package org.energyos.espi.thirdparty.domain;

import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

public class ReadingTypeTests {
    static final String XML_INPUT = "<ReadingType xmlns=\"http://naesb.org/espi\">" +
            "<accumulationBehaviour>Behaviour</accumulationBehaviour>" +
            "<commodity>Commodity</commodity>" +
            "<dataQualifier>DataQualifier</dataQualifier>" +
            "<flowDirection>FlowDirection</flowDirection>" +
            "<intervalLength>1</intervalLength>" +
            "<kind>Kind</kind>" +
            "<phase>Phase</phase>" +
            "<powerOfTenMultiplier>PowerOfTenMultiplier</powerOfTenMultiplier>" +
            "<timeAttribute>TimeAttribute</timeAttribute>" +
            "<uom>Uom</uom>" +
            "<consumptionTier>ConsumptionTier</consumptionTier>" +
            "<cpp>Cpp</cpp>" +
            "<currency>Currency</currency>" +
            "<interharmonic>" +
                "<numerator>1</numerator>" +
                "<denominator>2</denominator>" +
            "</interharmonic>" +
            "<measuringPeriod>MeasuringPeriod</measuringPeriod>" +
            "<tou>Tou</tou>" +
            "<aggregate>Aggregate</aggregate>" +
            "<argument>"+
                "<numerator>1</numerator>" +
                "<denominator>3</denominator>" +
            "</argument>" +
            "</ReadingType>";

    private ReadingType readingType;

    @Before
    public void before() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ReadingType.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        readingType = (ReadingType) unmarshaller.unmarshal(new ByteArrayInputStream(XML_INPUT.getBytes()));
    }

    @Test
    public void unmarshalsReadingType() {
        assertEquals(ReadingType.class, readingType.getClass());
    }

    @Test
    public void unmarshal_setsAccumulationBehaviour() {
        assertEquals("Behaviour",readingType.getAccumulationBehaviour());
    }

    @Test
    public void unmarshal_setsCommodity() {
        assertEquals("Commodity", readingType.getCommodity());
    }

    @Test
    public void unmarshal_setsDataQualifier() {
        assertEquals("DataQualifier", readingType.getDataQualifier());
    }

    @Test
    public void unmarshal_setsFlowDirection() {
        assertEquals("FlowDirection", readingType.getFlowDirection());
    }

    @Test
    public void unmarshal_setsIntervalLength() {
        assertEquals(new Long(1L), readingType.getIntervalLength());
    }

    @Test
    public void unmarshal_setsKind() {
        assertEquals("Kind", readingType.getKind());
    }

    @Test
    public void unmarshal_setsPhase() {
        assertEquals("Phase", readingType.getPhase());
    }

    @Test
    public void unmarshal_setsPowerOfTenMultiplier() {
        assertEquals("PowerOfTenMultiplier", readingType.getPowerOfTenMultiplier());
    }

    @Test
    public void unmarshal_setsTimeAttribute() {
        assertEquals("TimeAttribute", readingType.getTimeAttribute());
    }

    @Test
    public void unmarshal_setsUom() {
        assertEquals("Uom", readingType.getUom());
    }

    @Test
    public void unmarshal_setsConsumptionTier() {
        assertEquals("ConsumptionTier", readingType.getConsumptionTier());
    }

    @Test
    public void unmarshal_setsCpp() {
        assertEquals("Cpp", readingType.getCpp());
    }

    @Test
    public void unmarshal_setsCurrency() {
        assertEquals("Currency", readingType.getCurrency());
    }

    @Test
    public void unmarshal_setsInterharmonic() {
        ReadingInterharmonic interharmonic = new ReadingInterharmonic();
        interharmonic.setNumerator(new BigInteger("1"));
        interharmonic.setDenominator(new BigInteger("2"));

        assertEquals(interharmonic.getNumerator(), readingType.getInterharmonic().getNumerator());
        assertEquals(interharmonic.getDenominator(), readingType.getInterharmonic().getDenominator());
    }

    @Test
    public void unmarshal_setsMeasuringPeriod() {
        assertEquals("MeasuringPeriod", readingType.getMeasuringPeriod());
    }

    @Test
    public void unmarshal_setsTou() {
        assertEquals("Tou", readingType.getTou());
    }

    @Test
    public void unmarshal_setsAggregate() {
        assertEquals("Aggregate", readingType.getAggregate());
    }

    @Test
    public void unmarshal_setsArgument() {
        RationalNumber number = new RationalNumber();
        number.setNumerator(new BigInteger("1"));
        number.setDenominator(new BigInteger("3"));

        assertEquals(number.getNumerator(), readingType.getArgument().getNumerator());
        assertEquals(number.getDenominator(), readingType.getArgument().getDenominator());
    }

}
