package com.phasmidsoftware.dsaipg.sort;

import com.phasmidsoftware.dsaipg.util.Config;
import com.phasmidsoftware.dsaipg.util.StatPack;

import java.io.IOException;
import java.util.Comparator;
import java.util.Random;

public class SimpleHelper<X> implements Helper<X> {

    private final String description;
    private final Comparator<X> comparator;
    private final Instrument instrumenter;

    public SimpleHelper(String description, Comparator<X> comparator) {
        this.description = description;
        this.comparator = comparator;
        Instrument instrument = null;
        try {
            Config config = Config.load();  
            instrument = new Instrumenter(config);
        } catch (IOException e) {
            e.printStackTrace();  
            instrument = new Instrumenter(false, false, false, false, false, false, false); // 提供默认值
        }
        this.instrumenter = instrument;
    }

    @Override
    public Comparator<X> getComparator() {
        return comparator;
    }

    @Override
    public int compare(X x1, X x2) {
        instrumenter.incrementCompares();
        return comparator.compare(x1, x2);
    }

    @Override
    public void init(int n) {
        instrumenter.init(n, 1);
    }

    @Override
    public int getN() {
        return 0;
    }

    @Override
    public void close() {}

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public X[] random(int m, Class<X> clazz, java.util.function.Function<Random, X> f) {
        return null;
    }

    @Override
    public Helper<X> clone(String description, int N) {
        return new SimpleHelper<>(description, comparator);
    }

    @Override
    public Helper<X> clone(String description, Comparator<X> comparator, int N) {
        return new SimpleHelper<>(description, comparator);
    }

    @Override
    public boolean isShowStats() {
        return instrumenter.isShowStats();
    }

    @Override
    public StatPack getStatPack() {
        return instrumenter.getStatPack();
    }

    @Override
    public long getCopies() {
        return instrumenter.getCopies();
    }

    @Override
    public long getCompares() {
        return instrumenter.getCompares();
    }

    @Override
    public long getSwaps() {
        return instrumenter.getSwaps();
    }

    @Override
    public long getFixes() {
        return instrumenter.getFixes();
    }

    @Override
    public long getHits() {
        return instrumenter.getHits();
    }

    @Override
    public long getLookups() {
        return instrumenter.getLookups();
    }

    @Override
    public void incrementCopies(int n) {
        instrumenter.incrementCopies(n);
    }

    @Override
    public void incrementFixes(int n) {
        instrumenter.incrementFixes(n);
    }

    @Override
    public void incrementSwaps(int n) {
        instrumenter.incrementSwaps(n);
    }

    @Override
    public void incrementCompares() {
        instrumenter.incrementCompares();
    }

    @Override
    public void incrementHits(long n) {
        instrumenter.incrementHits(n);
    }

    @Override
    public void incrementLookups() {
        instrumenter.incrementLookups();
    }

    @Override
    public void gatherStatistic() {
        instrumenter.gatherStatistic();
    }

    @Override
    public boolean countFixes() {
        return instrumenter.countFixes();
    }

    @Override
    public void init(int n, int nRuns) {
        instrumenter.init(n, nRuns);
    }

    @Override
    public Config getConfig() {
        return null;
    }

    @Override
    public int pureComparison(X x1, X x2) {
        return comparator.compare(x1, x2);
    }

    @Override
    public void swapInto(X[] xs, int i, int j) {}
}
