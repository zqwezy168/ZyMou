package zy.library.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.View;

import java.util.Random;

/**
 * 自定义验证码
 * 
 * @Date 2016/5/11
 */
public class AuthCodeView extends View {

	/**
	 * 验证码长度
	 */
	private final int CODELENGTH = 4;

	/**
	 * 验证码字体大小
	 */
	private final int TEXTZISE = 40;

	/**
	 * 干扰线条的数目
	 */
	private final int LINENUM = 0;

	/**
	 * 验证码X坐标
	 */
	private int CODE_X;

	/**
	 * 验证码Y坐标
	 */
	private int CODE_Y;

	/**
	 * 验证码每个字符X轴之间的间距
	 */
	private final int PADDING_X = 30;

	/**
	 * 验证码每个字符Y轴之间的间距
	 */
	private final int PADDING_Y = 20;

	/**
	 * 每次随机生成验证码时X轴最大值
	 */
	private final int RANDOM_MAX_X = 20;

	/**
	 * 每次随机生成验证码时Y轴最大值
	 */
	private final int RANDOM_MAX_Y = 20;

	/**
	 * 如果State为True,则随机生成验证码 如果State为False,则生成用户输入框输入的验证码
	 */
	private boolean state = true;

	/**
	 * 验证码背景框的宽
	 */
	private final int WIDTH = 160;

	/**
	 * 验证码背景框的高
	 */
	private final int HEIGHT = 100;

	/**
	 * 验证码
	 */
	private String myCode;

	Paint paint;
	private Random random = new Random();
	private static final char[] CODES = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
			'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
			'X', 'Y', 'Z' };

	private SparseIntArray CODE_Xs = new SparseIntArray(CODELENGTH);
	private SparseIntArray CODE_Ys = new SparseIntArray(CODELENGTH);
	private SparseIntArray randomColors = new SparseIntArray(CODELENGTH);
	private SparseBooleanArray boldTexts = new SparseBooleanArray(CODELENGTH);
	private SparseIntArray startXs = new SparseIntArray(LINENUM);
	private SparseIntArray startYs = new SparseIntArray(LINENUM);
	private SparseIntArray stopXs = new SparseIntArray(LINENUM);
	private SparseIntArray stopYs = new SparseIntArray(LINENUM);
	private SparseIntArray lineColors = new SparseIntArray(LINENUM);

	public AuthCodeView(Context context) {
		super(context);
	}

	public AuthCodeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint();
	}

	Paint.FontMetricsInt fm;
	@Override
	protected void onDraw(Canvas canvas) {
		CODE_X = 0; // 将x轴位置设为0
		paint.setTextSize(TEXTZISE);// 设置字体大小
		paint.setColor(Color.rgb(210,210,210));
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
//		canvas.drawColor(Color.rgb(255, 255, 255));// 背景颜色

		paint.setStyle(Paint.Style.FILL);
		fm = paint.getFontMetricsInt();
		if (state) {
			// 如果为True 则随机生成验证码,否则使用用户输入的验证码
			myCode = createCode();
			for (int i = 0; i < CODELENGTH; i++) {
				CODE_X += PADDING_X + random.nextInt(RANDOM_MAX_X); // 随机生成验证码X轴的位置
				CODE_X = Math.min(CODE_X,getWidth() - PADDING_X);
//				CODE_X = getHeight() / 2 - fm.descent + (fm.bottom - fm.top) / 2;//居中
				CODE_Xs.put(i,CODE_X);
//				CODE_Y = PADDING_Y + random.nextInt(RANDOM_MAX_Y); // 随机生成验证码Y轴的位置
//				CODE_Y = Math.min(CODE_Y,getHeight());
				CODE_Y = getHeight() / 2 - fm.descent + (fm.bottom - fm.top) / 2;//居中
				CODE_Ys.put(i,CODE_Y);
				int randomColor = randomColor();
				randomColors.put(i,randomColor);
				paint.setColor(randomColor);
				boolean boldText = random.nextBoolean();
				boldTexts.put(i,boldText);
				paint.setFakeBoldText(boldText);
				canvas.drawText(myCode.charAt(i) + "", CODE_X, CODE_Y, paint);
			}
			for (int i = 0; i < LINENUM; i++) {
				int startX = random.nextInt(getWidth()); // 线条起始X坐标
				int startY = random.nextInt(getHeight()); // 线条起始Y坐标
				int stopX = random.nextInt(getWidth()); // 线条结束X坐标
				int stopY = random.nextInt(getHeight()); // 线条结束Y坐标
				/*startX = startX < getLeft() ? getLeft() : startX;
				startY = startY < getTop() ? getTop() : startY;
				stopX = stopX > getWidth() ? getWidth() : stopX;
				stopY = stopY > getHeight() ? getHeight() : stopY;*/
				startXs.put(i,startX);
				startYs.put(i,startY);
				stopXs.put(i,stopX);
				stopYs.put(i,stopY);
				lineColors.put(i,randomColor());
				drawLine(i,canvas, paint);
			}
			state = !state;
		}
		// 画验证码
		for (int i = 0; i < CODELENGTH; i++) {
			paint.setColor(randomColors.get(i));
			paint.setFakeBoldText(boldTexts.get(i));// 随机粗体/非粗体
			canvas.drawText(myCode.charAt(i) + "", CODE_Xs.get(i), CODE_Ys.get(i), paint);
		}

		// 画干扰线条
		for (int i = 0; i < LINENUM; i++) {
			drawLine(i,canvas, paint);
		}
	}

	/**
	 * 随机生成验证码
	 * 
	 * @return
	 */
	private String createCode() {
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < CODELENGTH; i++) {
			buffer.append(CODES[random.nextInt(CODES.length)]);
		}
		return buffer.toString();
	}

	/***
	 * 画干扰线条
	 * 
	 * @param canvas
	 * @param paint
	 */
	private void drawLine(int position, Canvas canvas, Paint paint) {
		paint.setStrokeWidth(2);	// 设置线条的粗
		paint.setColor(lineColors.get(position)); // 设置线条颜色
		canvas.drawLine(startXs.get(position), startYs.get(position), stopXs.get(position), stopYs.get(position), paint);// 画干扰线条
	}

	/***
	 * 随机生成RGB
	 *
	 * @return
	 */
	private int randomColor() {
		int red = random.nextInt(256);
		int green = random.nextInt(256);
		int blue = random.nextInt(256);
		return Color.rgb(red, green, blue);
	}

	/**
	 * 刷新验证码
	 */
	public void refresh(boolean userState, String Code) {
		myCode = Code;
		state = userState;
		invalidate();
	}

	public String getCode() {
		return myCode;
	}

}
