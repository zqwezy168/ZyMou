package zy.library.ui.chatLib.interfaces.dataprovider;

import zy.library.ui.chatLib.components.YAxis;
import zy.library.ui.chatLib.data.LineData;

public interface LineDataProvider extends BarLineScatterCandleBubbleDataProvider {

    LineData getLineData();

    YAxis getAxis(YAxis.AxisDependency dependency);
}
