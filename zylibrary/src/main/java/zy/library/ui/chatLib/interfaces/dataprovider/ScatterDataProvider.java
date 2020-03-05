package zy.library.ui.chatLib.interfaces.dataprovider;

import zy.library.ui.chatLib.data.ScatterData;

public interface ScatterDataProvider extends BarLineScatterCandleBubbleDataProvider {

    ScatterData getScatterData();
}
