package site.to0mi1.s3sample.application.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

/**
 * トップ画面の投稿一覧のページラッパークラス。
 * 画面に表示するページナビゲーションの数を制御する目的。
 * 
 * @author tomiyama
 *
 * @param <T>
 */
public class PageWrapper<T> {
	public static final int MAX_PAGE_ITEM_DISPLAY = 10;
	private Page<T> page;
	private List<PageItem> items;
	/**
	 * 現在のページ。0からカウントされる。
	 */
	private int currentNumber;
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public PageWrapper(Page<T> page, String url){
		this.page = page;
		this.url = url;
		items = new ArrayList<PageItem>();
		
		// 画面に表示する目的で利用させるためプラス1する。
		currentNumber = page.getNumber() + 1;

		// 画面表示するページナビゲーションの生成
		int start, size;
		if (page.getTotalPages() <= MAX_PAGE_ITEM_DISPLAY){
			// 前ページの数が、最大表示ページ数以下の場合には、表示開始ページを1に設定する。
			start = 1;
			size = page.getTotalPages();
		} else {
			// 表示するページが最大表示ページ数の半分以下の場合、表示中のページをページナビゲーション中央に持ってきたときに、
			// 開始ページが負の値にになるのを抑止するため、ナビゲーション表示開始ページを1に設定
			if (currentNumber <= MAX_PAGE_ITEM_DISPLAY - MAX_PAGE_ITEM_DISPLAY/2){
				start = 1;
				size = MAX_PAGE_ITEM_DISPLAY;
			} else if (currentNumber >= page.getTotalPages() - MAX_PAGE_ITEM_DISPLAY/2){
				// 上の条件と逆の理由。最終ページの表示制御。実際の総ページ数を超えないため
				start = page.getTotalPages() - MAX_PAGE_ITEM_DISPLAY + 1;
				size = MAX_PAGE_ITEM_DISPLAY;
			} else {
				// 表示開始ページを現在表示中のページを中央に起き、現在のページから総ページ数の半分を引いた数を表示開始ページとする。
				start = currentNumber - MAX_PAGE_ITEM_DISPLAY/2;
				size = MAX_PAGE_ITEM_DISPLAY;
			}
		}
		// 表示するページを設定する
		for (int i = 0; i<size; i++){
			items.add(new PageItem(start+i, (start+i)==currentNumber));
		}
	}

	public List<PageItem> getItems(){
		return items;
	}

	public int getNumber(){
		return currentNumber;
	}

	public List<T> getContent(){
		return page.getContent();
	}

	public int getSize(){
		return page.getSize();
	}

	public int getTotalPages(){
		return page.getTotalPages();
	}

	public boolean isFirstPage(){
		return page.isFirst();
	}

	public boolean isLastPage(){
		return page.isLast();
	}

	public boolean isHasPreviousPage(){
		return page.hasPrevious();
	}

	public boolean isHasNextPage(){
		return page.hasNext();
	}

	public class PageItem {
		private int number;
		private boolean current;
		public PageItem(int number, boolean current){
			this.number = number;
			this.current = current;
		}

		public int getNumber(){
			return this.number;
		}

		public boolean isCurrent(){
			return this.current;
		}
	}
}
