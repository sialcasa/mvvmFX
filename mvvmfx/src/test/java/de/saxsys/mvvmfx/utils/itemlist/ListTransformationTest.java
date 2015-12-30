package de.saxsys.mvvmfx.utils.itemlist;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import org.junit.Test;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class ListTransformationTest {

    class Source {
        public Source(String value) {
            this.value.set(value);
        }

        StringProperty value = new SimpleStringProperty();
    }

    class Target {
        public Target(Source source) {
            value.bind(source.value);
        }

        StringProperty value = new SimpleStringProperty();
    }

    @Test
    public void testSimpleTransformFunctionWithoutExtractor() {

        ObservableList<Source> sourceList = FXCollections.observableArrayList();

        final Function<Source, Target> transformFunction = Target::new;

        final ListTransformation<Source, Target> targetListTransformation = new ListTransformation<>(sourceList, transformFunction);
        final ObservableList<Target> targetList = targetListTransformation.getTargetList();

        final Source source1 = new Source("1");
        sourceList.add(source1);


        assertThat(targetList).hasSize(1);
        final Target target1 = targetList.get(0);

        assertThat(target1.value.get()).isEqualTo("1");



        source1.value.setValue("other");
        assertThat(target1.value.get()).isEqualTo("other");

        assertThat(targetList).containsOnly(target1);
    }

    @Test
    public void testSimpleTransformFunctionWithExtractor() {

        final Callback<Source, Observable[]> extractor = source -> new Observable[]{source.value};

        ObservableList<Source> sourceList = FXCollections.observableArrayList(extractor);

        final Function<Source, Target> transformFunction = Target::new;

        final ListTransformation<Source, Target> targetListTransformation = new ListTransformation<>(sourceList, transformFunction);
        final ObservableList<Target> targetList = targetListTransformation.getTargetList();

        final Source source1 = new Source("1");
        sourceList.add(source1);


        assertThat(targetList).hasSize(1);
        final Target target1 = targetList.get(0);

        assertThat(target1.value.get()).isEqualTo("1");



        source1.value.setValue("other");
        assertThat(target1.value.get()).isEqualTo("other");

        assertThat(targetList).doesNotContain(target1); // target is replaced with another instance
        final Target target2 = targetList.get(0);

        assertThat(target2).isNotEqualTo(target1);
    }


    @Test
    public void testExtendedTransformFunctionWithExtractor() {

        final Callback<Source, Observable[]> extractor = source -> new Observable[]{source.value};

        ObservableList<Source> sourceList = FXCollections.observableArrayList(extractor);


        final BiFunction<Source, Target, Target> transformFunction = (source, oldValue) -> {
            if(oldValue == null) {
                return new Target(source);
            } else {
                return oldValue;
            }
        };



        final ListTransformation<Source, Target> targetListTransformation = new ListTransformation<>(sourceList, transformFunction);
        final ObservableList<Target> targetList = targetListTransformation.getTargetList();

        final Source source1 = new Source("1");
        sourceList.add(source1);


        assertThat(targetList).hasSize(1);
        final Target target1 = targetList.get(0);

        assertThat(target1.value.get()).isEqualTo("1");



        source1.value.setValue("other");
        assertThat(target1.value.get()).isEqualTo("other");

        assertThat(targetList).containsOnly(target1);
        final Target target2 = targetList.get(0);

        assertThat(target2).isEqualTo(target1);
    }


}
