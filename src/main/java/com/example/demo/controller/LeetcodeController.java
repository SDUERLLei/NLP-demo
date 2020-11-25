package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

/**
 * Created by liulei03 on 2020/10/28.
 */
@Controller
@RequestMapping("/leet")
public class LeetcodeController {
    @RequestMapping("/index")
    public void index(){
        int [] arr={3,2,4};
        int target = 6;
        int []result = this.twoSum(arr,target);
        String s="pwwkew";
        System.out.print(this.lengthOfLongestSubstring(s));
    }

    public boolean uniqueOccurrences(int[] arr) {
        boolean flag =true;
        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int i=0;i<arr.length;i++){
            if(!list.contains(arr[i])){
                list.add(arr[i]);
            }
        }
        System.out.println(list);
        int [] time = new int[list.size()];
        for(int i=0;i<list.size();i++){
            time[i]=0;
            for(int n=0;n<arr.length;n++){
                if(list.get(i)==arr[n]){
                    time[i]++;
                }
            }
        }
        System.out.println(time);
        for(int i=0;i<time.length;i++){
            for(int n=i;n<time.length-1;n++){
                if(time[i]==time[n+1]){
                    flag=false;
                }
            }
        }
        return flag;
    }

    //两数之和
    public int[] twoSum(int[] nums, int target) {
         int[]result = new int[2];
         for(int i=0;i<nums.length;i++){
             for(int n=i+1;n<nums.length;n++){
                 if(nums[i]+nums[n]==target){
                     result[0]=i;
                     result[1]=n;
                     return result;
                 }
             }
         }
         return result;
    }

    //无重复字符的最长子串
    public int lengthOfLongestSubstring(String s) {
        int res=0;
        char [] str = new char [s.length()];
        int []temp = new int[s.length()];
        str=s.toCharArray();
        for(int i=0;i<str.length;i++){
            ArrayList list= new ArrayList();
            list.add(str[i]);
            for(int n=i+1;n<str.length;n++){
                if(!list.contains(str[n])){
                       list.add(str[n]);
                }else{
                    break;
                }
            }
            temp[i]=list.size();
        }
        for(int i=0;i<temp.length;i++){
            if(temp[i]>res){
                res=temp[i];
            }
        }
        return res;
    }


}
