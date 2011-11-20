package org.cmayes.hartree.parser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.joda.time.Duration;

public class CalculationResult {
    private List<Date> terminationDates = new ArrayList<Date>();
    private List<Duration> cpuTimes = new ArrayList<Duration>();
    private Double transPart;
    private Double rotPart;
    private Double elecEn;
    private Integer mult;
    private Integer atomCount;
    private List<Double> frequencyValues = new ArrayList<Double>();

    public Double getElecEn() {
        return elecEn;
    }

    public void setElecEn(Double elecEn) {
        this.elecEn = elecEn;
    }

    public Integer getMult() {
        return mult;
    }

    public void setMult(Integer mult) {
        this.mult = mult;
    }

    public Double getRotPart() {
        return rotPart;
    }

    public void setRotPart(Double rotPart) {
        this.rotPart = rotPart;
    }

    public Double getTransPart() {
        return transPart;
    }

    public void setTransPart(Double transPart) {
        this.transPart = transPart;
    }

    public Integer getAtomCount() {
        return atomCount;
    }

    public void setAtomCount(Integer atomCount) {
        this.atomCount = atomCount;
    }

    public List<Double> getFrequencyValues() {
        return frequencyValues;
    }

    public void setFrequencyValues(List<Double> frequencyValues) {
        this.frequencyValues = frequencyValues;
    }

    public List<Date> getTerminationDates() {
        return terminationDates;
    }

    public void setTerminationDates(List<Date> terminationDates) {
        this.terminationDates = terminationDates;
    }

    public List<Duration> getCpuTimes() {
        return cpuTimes;
    }

    public void setCpuTimes(List<Duration> durations) {
        this.cpuTimes = durations;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this)
                .append(String.format("cpuTimes(%d)", this.cpuTimes.size()),
                        this.cpuTimes)
                .append(String.format("terminationDates(%d)",
                        this.terminationDates.size()), this.terminationDates)
                .append("transPart", this.transPart)
                .append("elecEn", this.elecEn)
                .append(String.format("frequencyValues(%d)",
                        this.frequencyValues.size()), this.frequencyValues)
                .append("mult", this.mult).append("atomCount", this.atomCount)
                .append("rotPart", this.rotPart).toString();
    }
}
