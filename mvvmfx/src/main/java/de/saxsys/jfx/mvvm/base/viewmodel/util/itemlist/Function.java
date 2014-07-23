package de.saxsys.jfx.mvvm.base.viewmodel.util.itemlist;

/**
 * Can map from a SourceType to a TargetType.
 *
 */
public interface Function<SourceType, TargetType> {

    /**
     * Maps from an element of type SourceType to an element of type TargetType.
     *
     * @param source the element to map
     * @return the mapped element of type TargetType
     */
    TargetType apply(SourceType source);
}
