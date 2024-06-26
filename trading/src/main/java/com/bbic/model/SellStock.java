package com.bbic.model;

import com.bbic.model.dto.User;
import com.bbic.model.dto.UserStockDTO;

import java.util.Scanner;

public class SellStock {

    User user;
    UserStockDTO stock;

    public SellStock(User user) {
        this.user = user;
        /*임시 주식 data*/
//        this.user.userStocks.add(new UserStockDTO(1, 10, 83000, 1234));
//        this.user.userStocks.add(new UserStockDTO(2, 10, 183000, 1234));
    }

    public void SellStock() {  // 주식 매도 시작 메소드
        // 매도 가능한 주식을 표시
        System.out.println("===================보유 주식=====================");
        System.out.println(user.showHoldingStocks());
        System.out.println("================================================");
        Scanner sc = new Scanner(System.in);
        System.out.print("매도할 주식 번호를 입력하세요 : ");
        this.stock = selectedStock(sc.nextInt());
        if (this.stock != null) {
            int temp = insertSellingAmount();   // 매도 수량 입력
        } else {
            System.out.println("보유하지 않거나 존재하지 않은 종목입니다.");
        }
    }

    public UserStockDTO selectedStock(int selection) {  // 입력한 코드를 가진 주식을 보유하고 있는지 확인
        for (UserStockDTO stock : user.userStocks) {
            if (stock.getStockCode() == selection) {    // 만일 보유하고 있다면 해당 주식 정보를 리턴
                return stock;
            }
        }
        return null;
    }

    //2. 선택 종목 정보를 보여주는 메소드 ( )
    public int insertSellingAmount() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            //현재 보유 수량(stock.get~)
            //거래 가능 수량(=현재 보유량)
            System.out.print("매도 수량을 입력하세요." );
            System.out.println("현재 수량 : " + this.stock.getCount());
            System.out.print("수량 입력(0이면 메인메뉴로 돌아갑니다. ) : ");
            int amount = sc.nextInt();
            //현재 잔여 예수금
            //매도 수량 입력
            if (amountCheck(amount)) {
                return amount;
            }
            System.out.println("값을 똑!바!로! 입력해주세요!");
        }
    }

    //매도 재확인 메세지 메소드( )
    public void sellCheck(int count) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("매도 수량은 " + count + "이며 총 매도 금액은 " + count * stock.getStockData().getPrice() + " 입니다. \n 매도하시겠습니까? (Y/N) : ");
            String select = sc.next().toUpperCase();
            if (select.equals("Y") ){
                decreaseHoldingStock(count);
                System.out.println("거래가 완료되었습니다.");
                infoStock();
                return;
            }else if (select.equals("N")) {
                System.out.println("거래를 취소하고 메인으로 돌아갑니다.");
                return;
            } else {
                System.out.println("잘못된 값을 입력했습니다. 다시 입력해주세요.");
            }
        }
    }

    //매도 수량 비교 메소드 ( )
    public boolean amountCheck(int amount) {

        boolean check = false;

        if (stock.getCount() >= amount && amount > 0) {
            check = true;
            sellCheck(amount);

            return check;
        }
        return check;
    }

    //사용자 매도 수량, 보유 수량 수정 메소드 ( )
    public void decreaseHoldingStock(int count) {
        stock.setCount(stock.getCount() - count);
        user.setDeposit(user.getDeposit() + (stock.getStockData().getPrice() * count));
    }

    //매도 종목 정보 메소드 ( )
    public void infoStock() {

        System.out.println("=============매도 종목 정보 확인=============");
        System.out.println("종목명 : " + stock.getStockData().getStockName());
        System.out.println("평단가 : " + stock.getAveragePrice());
        System.out.println("현재 보유 수량 : " + stock.getCount());
        System.out.println("현재 잔여 예수금 : " + user.getDeposit());
        System.out.println("==========================================");

    }


}
