package bj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Card {

	Card(){
		//空の山札を作成
		List<Integer> deck = new ArrayList<>(52);
		//山札をシャッフル
		shuffleDeck(deck);

		//プレイヤー・ディーラーの手札リストを生成
		List<Integer> player = new ArrayList<>();
		List<Integer> dealer = new ArrayList<>();

		//プレイヤー・ディーラーがカードを2枚引く
		player.add(deck.get(0));
		dealer.add(deck.get(1));
		player.add(deck.get(2));
		dealer.add(deck.get(3));
	}

	//山札（deck）に値を入れ、シャッフルするメソッド
	static void shuffleDeck(List<Integer> deck) {

		// リストに1-52の連番を代入
		for (int i = 1; i <= 52; i++) {
			deck.add(i);
		}

		//山札をシャッフル
		Collections.shuffle(deck);

	}

	//手札がバーストしているか判定するメソッド
	static boolean isBusted(int point) {
		if (point <= 21) {
			return false;
		} else {
			return true;
		}
	}

	//現在の合計ポイントを計算するメソッド
	static int sumPoint(List<Integer> list) {
		int sum = 0;

		for (int i = 0; i < list.size(); i++) {
			sum = sum + toPoint(toNumber(list.get(i)));
		}

		return sum;
	}

	//トランプの数字を得点計算用のポイントに変換するメソッド.J/Q/Kは10とする
	static int toPoint(int num) {
		if (num == 11 || num == 12 || num == 13) {
			num = 10;
		}

		return num;
	}

	//山札の数を（スート）の（ランク）の文字列に置き換えるメソッド
	static String toDescription(int cardNumber) {
		String rank = toRank(toNumber(cardNumber));
		String suit = toSuit(cardNumber);

		return suit + " / " + rank;
	}

	//山札の数をカードの数に置き換えるメソッド
	static int toNumber(int cardNumber) {
		int number = cardNumber % 13;
		if (number == 0) {
			number = 13;
		}

		return number;
	}

	//カード番号をランク（AやJ,Q,K）に変換するメソッド
	static String toRank(int number) {
		switch (number) {
		case 1:
			return "A";
		case 11:
			return "J";
		case 12:
			return "Q";
		case 13:
			return "K";
		default:
			String str = String.valueOf(number);
			return str;
		}
	}

	//山札の数をスート（ハートやスペードなどのマーク）に置き換えるメソッド
	static String toSuit(int cardNumber) {
		switch ((cardNumber - 1) / 13) {
		case 0:
			return "♣";
		case 1:
			return "♢";
		case 2:
			return "♡";
		case 3:
			return "♠";
		default:
			return "例外です";
		}
	}
}


