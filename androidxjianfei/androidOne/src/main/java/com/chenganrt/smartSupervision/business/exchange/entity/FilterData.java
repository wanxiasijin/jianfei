package com.chenganrt.smartSupervision.business.exchange.entity;

import com.android.commonlib.okhttp.bean.TypeBean;

import java.util.ArrayList;
import java.util.List;

public class FilterData {
    public static final int WASTE_TYPE = 0;
    public static final int CONSTRUCTION_WASTE = 1;
    public static final int AREA = 2;
    public static final int COMPREHENSIVE_UTILIZATION = 3;
    private static List<TypeBean> mTypeList = new ArrayList<TypeBean>();

    public static void setType(List<TypeBean> typeList) {
        mTypeList.clear();
        mTypeList.addAll(typeList);
    }

    private static List<TypeBean> getTypeList() {
        return mTypeList;
    }

    public static List<TypeBean> getTypes(int index) {
        switch (index) {
            case WASTE_TYPE:
                List<TypeBean> typeList0 = new ArrayList<>();
                TypeBean typeBean01 = new TypeBean("construction_waste", null, "建筑废弃物");
                TypeBean typeBean02 = new TypeBean("comprehensive_utilization", null, "综合利用产品");
                typeList0.add(typeBean01);
                typeList0.add(typeBean02);
                return typeList0;
            case CONSTRUCTION_WASTE:
                for (TypeBean typeBean : getTypeList()) {
                    if (typeBean.getCode().equals("construction_waste")) {
                        return typeBean.getChildren();
                    }
                }
                List<TypeBean> typeList1 = new ArrayList<>();
                TypeBean typeBean11 = new TypeBean("engineering_dregs", null, "工程渣土");
                TypeBean typeBean12 = new TypeBean("engineering_mud", null, "工程泥浆");
                TypeBean typeBean13 = new TypeBean("build_waste", null, "施工废弃物");
                TypeBean typeBean14 = new TypeBean("demolition_waste", null, "拆除废弃物");
                TypeBean typeBean15 = new TypeBean("renovation_waste", null, "装修废弃物");
                typeList1.add(typeBean11);
                typeList1.add(typeBean12);
                typeList1.add(typeBean13);
                typeList1.add(typeBean14);
                typeList1.add(typeBean15);
                return typeList1;
            case AREA:
                for (TypeBean typeBean : getTypeList()) {
                    if (typeBean.getCode().equals("area")) {
                        return typeBean.getChildren();
                    }
                }
                List<TypeBean> typeList3 = new ArrayList<>();
                TypeBean typeBean31 = new TypeBean("lhq", null, "罗湖区");
                TypeBean typeBean32 = new TypeBean("ftq", null, "福田区");
                TypeBean typeBean33 = new TypeBean("nsq", null, "南山区");
                TypeBean typeBean34 = new TypeBean("baq", null, "宝安区");
                TypeBean typeBean35 = new TypeBean("ytq", null, "盐田区");
                TypeBean typeBean36 = new TypeBean("lgq", null, "龙岗区");
                TypeBean typeBean37 = new TypeBean("lhxq", null, "龙华新区");
                TypeBean typeBean38 = new TypeBean("gmxq", null, "光明新区");
                TypeBean typeBean39 = new TypeBean("dpxq", null, "大鹏新区");
                TypeBean typeBean30 = new TypeBean("psxq", null, "坪山新区");
                typeList3.add(typeBean31);
                typeList3.add(typeBean32);
                typeList3.add(typeBean33);
                typeList3.add(typeBean34);
                typeList3.add(typeBean35);
                typeList3.add(typeBean36);
                typeList3.add(typeBean37);
                typeList3.add(typeBean38);
                typeList3.add(typeBean39);
                typeList3.add(typeBean30);
                return typeList3;
            case COMPREHENSIVE_UTILIZATION:
                for (TypeBean typeBean : getTypeList()) {
                    if (typeBean.getCode().equals("comprehensive_utilization")) {
                        return typeBean.getChildren();
                    }
                }
                List<TypeBean> typeList2 = new ArrayList<>();
                TypeBean typeBean21 = new TypeBean("recycled_aggregate_concrete", null, "再生骨料混泥土");
                TypeBean typeBean22 = new TypeBean("recycled_aggregate_mortar", null, "再生骨料混砂浆");
                TypeBean typeBean23 = new TypeBean("recycled_products_masonry_block", null, "砌体块材料再生产品");
                TypeBean typeBean24 = new TypeBean("plate_recycling_products", null, "板材类再生产品");
                TypeBean typeBean25 = new TypeBean("recycled_aggregate", null, "再生骨料");
                typeList2.add(typeBean21);
                typeList2.add(typeBean22);
                typeList2.add(typeBean23);
                typeList2.add(typeBean24);
                typeList2.add(typeBean25);
                return typeList2;
            default:
                return null;
        }
    }
}
