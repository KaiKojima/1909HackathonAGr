package testJsp;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bj_main {

	public static void main(String[] args) {

		Card c = new Card();
		Scanner scanner = new Scanner(System.in);

		System.out.println("ゲームを開始します");

		int maxBet = 10;
		int bet = 0;

		for (int i = 1; i <= 10; i++) {
			System.out.print(i + "試合目");
			while (true) {

				System.out.print("掛け金：");
				bet = scanner.nextInt();

				if (bet > maxBet) {
					System.out.println("無効");
					System.out.println("現在" + maxBet + "枚");
					System.out.println(maxBet + "枚以下で選択してください。");
					continue;
				}
				System.out.println(bet + "で開始");
				break;
			}

			//空の山札を作成
			List<Integer> deck = new ArrayList<>(52);
			//山札をシャッフル
			c.shuffleDeck(deck);

			//プレイヤー・ディーラーの手札リストを生成
			List<Integer> player = new ArrayList<>();
			List<Integer> dealer = new ArrayList<>();

			//プレイヤー・ディーラーがカードを2枚引く
			player.add(deck.get(0));
			dealer.add(deck.get(1));
			player.add(deck.get(2));
			dealer.add(deck.get(3));

			//山札の進行状況を記録する変数deckCountを定義
			int deckCount = 4;

			//プレイヤーの手札枚数を記録する変数playerHandsを定義
			int playerHands = 2;

			//プレイヤー・ディーラーの手札のポイントを表示
			System.out.println();
			System.out.println("あなたの1枚目のカードは" + c.toDescription(player.get(0)));
			System.out.println();

			System.out.println("ディーラーの1枚目のカードは" + c.toDescription(dealer.get(0)));
			System.out.println();

			System.out.println("あなたの2枚めのカードは" + c.toDescription(player.get(1)));
			System.out.println();

			System.out.println("ディーラーの2枚めのカードは秘密です。");
			System.out.println();

			//プレイヤー・ディーラーのポイントを集計
			int playerPoint = c.sumPoint(player);
			int dealerPoint = c.sumPoint(dealer);

			System.out.println("あなたの現在のポイントは" + playerPoint + "です。");

			//プレイヤーがカードを引くフェーズ
			while (true) {
				System.out.println();
				System.out.println("カードを引きますか？Yes:y or No:n");
				Scanner scan = new Scanner(System.in);
				String str = scan.next();

				if ("n".equals(str)) {
					break;
				} else if ("y".equals(str)) {
					//手札に山札から1枚加える
					player.add(deck.get(deckCount));
					//山札と手札を一枚進める
					deckCount++;
					playerHands++;

					System.out.println("あなたの" + playerHands + "枚目のカードは" + c.toDescription(player.get(playerHands - 1)));
					playerPoint = c.sumPoint(player);
					System.out.println("現在の合計は" + playerPoint);
					//プレイヤーのバーストチェック
					if (c.isBusted(playerPoint)) {
						//System.out.println("残念、バーストしてしまいました。");

						break;
//						maxBet = maxBet - bet;
//						System.out.println("残金：" + maxBet);
					}
				} else {
					System.out.println("あなたの入力は" + str + " です。y か n を入力してください。");
				}
			}

			//ディーラーが手札を17以上にするまでカードを引くフェーズ
			while (true) {
				//手札が17以上の場合ブレーク
				if (dealerPoint >= 17 ) {
					break;
				} else {
					//手札に山札から1枚加える
					dealer.add(deck.get(deckCount));
					//山札を一枚進める
					deckCount++;

					//ディーラーの合計ポイントを計算
					dealerPoint = c.sumPoint(dealer);

				}

			}

			System.out.println("ディーラーの合計は" + dealerPoint);
			System.out.println();

			if (playerPoint == dealerPoint || ( playerPoint > 21 && dealerPoint > 21)) {
				System.out.println("引き分けです。");
			} else if ((dealerPoint > 21 && playerPoint <= 21)) {
				System.out.println("勝ちました！");
				if (playerPoint == 21) {
					maxBet = maxBet + bet * 2;
					System.out.println("残金：" + maxBet);
				} else {
					maxBet = maxBet + bet;
					System.out.println("残金：" + maxBet);
				}
			} else if (playerPoint <= 21 && playerPoint > dealerPoint  ) {
				System.out.println("勝ちました！");
				if (playerPoint == 21) {
					maxBet = maxBet + bet * 2;
					System.out.println("残金：" + maxBet);
				} else {
					maxBet = maxBet + bet;
					System.out.println("残金：" + maxBet);
				}
			} else {
				System.out.println("負けました・・・");
				maxBet = maxBet - bet;
				if(maxBet <= 0) {
					System.out.println("残金がマイナスになりました");
					maxBet = 0;
					break;
				}
				System.out.println("残金：" + maxBet);
			}
//
//			if(maxBet < 0) {
//				System.out.println("残金がマイナスになりました");
//				break;
//			}

		}
		System.out.println("最終コイン" + maxBet);
	}

}
