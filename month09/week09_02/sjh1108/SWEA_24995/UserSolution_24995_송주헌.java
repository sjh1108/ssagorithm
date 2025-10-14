package week09_02.sjh1108.SWEA_24995;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

class UserSolution_24995_송주헌
{
    static class Product implements Comparable<Product>{
        int idx;
        int price;

        public Product(int idx, int price){
            this.idx = idx;
            this.price = price;
        }

        @Override
        public int compareTo(Product p){
            if(this.price != p.price){
                return Integer.compare(this.price, p.price);
            }

            return Integer.compare(this.idx, p.idx);
        }
    }

    static class ProductGroup{
        TreeSet<Product> productSet;
        int discount;
        int cnt;

        public ProductGroup(){
            this.productSet = new TreeSet<>();
            this.discount = 0;
            this.cnt = 0;
        }
    }

    static class ProductInfo{
        int mID;
        int mCategory;
        int mCompany;
        int mPrice;
        boolean mClosed;

        public ProductInfo(int mID, int mCategory, int mCompany, int mPrice){
            this.mID = mID;
            this.mCategory = mCategory;
            this.mCompany = mCompany;
            this.mPrice = mPrice;
        }
    }

    int productIdx;
    HashMap<Integer, Integer> IDtoIDX;
    ProductInfo[] productArray;
    ProductGroup[][] categoryCompanyGroup;
    
    
    public void init()
    {
        productIdx = 1;
        IDtoIDX = new HashMap<>();
        productArray = new ProductInfo[50005];
        categoryCompanyGroup = new ProductGroup[6][6];

        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 6; j++){
                categoryCompanyGroup[i][j] = new ProductGroup();
            }
        }

        return;
    }

    public int sell(int mID, int mCategory, int mCompany, int mPrice)
    {
        ProductGroup group = categoryCompanyGroup[mCategory][mCompany];

        int tmpPrice = mPrice + group.discount;

        productArray[productIdx] = new ProductInfo(mID, mCategory, mCompany, tmpPrice);
        IDtoIDX.put(mID, productIdx);
        productIdx++;

        group.productSet.add(new Product(mID, tmpPrice));
        group.cnt++;

        return group.cnt;
    }

    public int closeSale(int mID)
    {
        Integer idx = IDtoIDX.get(mID);

        if(idx == null || productArray[idx].mClosed){
            return -1;
        }

        productArray[idx].mClosed = true;
        ProductInfo info = productArray[idx];

        categoryCompanyGroup[info.mCategory][info.mCompany].cnt--;

        return info.mPrice - categoryCompanyGroup[info.mCategory][info.mCompany].discount;
    }

    public int discount(int mCategory, int mCompany, int mAmount)
    {
        ProductGroup group = categoryCompanyGroup[mCategory][mCompany];
        group.discount += mAmount;

        Iterator<Product> iter = group.productSet.iterator();

        while(iter.hasNext()){
            Product tmp = iter.next();

            if(tmp.price > group.discount){
                break;
            }

            int tmpIdx = IDtoIDX.get(tmp.idx);

            if(!productArray[tmpIdx].mClosed){
                group.cnt--;
                productArray[tmpIdx].mClosed = true;
            }

            iter.remove();
        }

        return group.cnt;
    }

    Solution_24995.RESULT show(int mHow, int mCode)
    {
        Solution_24995.RESULT res = new Solution_24995.RESULT();

        List<Product> list = new ArrayList<>();

        for(int i = 1; i < 6; i++){
            if(mHow == 1 && i != mCode) continue;
            for(int j = 1; j < 6; j++){
                if(mHow == 2 && j != mCode) continue;

                ProductGroup group = categoryCompanyGroup[i][j];

                int cnt = 0;

                for(Product p: group.productSet){
                    if(cnt >= 5) break;

                    int idx = IDtoIDX.get(p.idx);

                    if(!productArray[idx].mClosed){
                        int price = p.price - group.discount;

                        list.add(new Product(p.idx, price));
                        cnt++;
                    }
                }
            }
        }

        Collections.sort(list);

        res.cnt = Math.min(list.size(), 5);
        for(int i = 0; i < res.cnt; i++){
            res.IDs[i] = list.get(i).idx;
        }
        
        return res;
    }
}