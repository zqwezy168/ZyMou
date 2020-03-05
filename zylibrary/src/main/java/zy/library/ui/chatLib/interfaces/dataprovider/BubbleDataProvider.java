package zy.library.ui.chatLib.interfaces.dataprovider;

import zy.library.ui.chatLib.data.BubbleData;

public interface BubbleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    BubbleData getBubbleData();
}
