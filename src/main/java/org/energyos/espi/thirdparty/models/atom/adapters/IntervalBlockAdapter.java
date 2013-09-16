package org.energyos.espi.thirdparty.models.atom.adapters;

import org.energyos.espi.thirdparty.domain.IntervalBlock;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class IntervalBlockAdapter extends XmlAdapter<JAXBElement<IntervalBlock>, IntervalBlock> {
    @Override
    public IntervalBlock unmarshal(JAXBElement<IntervalBlock> v) throws Exception {
        return v.getValue();
    }

    @Override
    public JAXBElement<IntervalBlock> marshal(IntervalBlock v) throws Exception {
        return null;
    }
}
