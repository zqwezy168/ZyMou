package zy.library.ui.chatLib.interfaces.dataprovider;

import zy.library.ui.chatLib.data.CandleData;

public interface CandleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    CandleData getCandleData();
}
