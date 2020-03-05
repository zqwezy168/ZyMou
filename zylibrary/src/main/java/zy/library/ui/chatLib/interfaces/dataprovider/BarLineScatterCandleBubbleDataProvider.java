package zy.library.ui.chatLib.interfaces.dataprovider;

import zy.library.ui.chatLib.components.YAxis.AxisDependency;
import zy.library.ui.chatLib.data.BarLineScatterCandleBubbleData;
import zy.library.ui.chatLib.utils.Transformer;

public interface BarLineScatterCandleBubbleDataProvider extends ChartInterface {

    Transformer getTransformer(AxisDependency axis);
    boolean isInverted(AxisDependency axis);
    
    float getLowestVisibleX();
    float getHighestVisibleX();

    BarLineScatterCandleBubbleData getData();
}
