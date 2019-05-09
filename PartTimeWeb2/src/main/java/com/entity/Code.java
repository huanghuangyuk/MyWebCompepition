package com.entity;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.ServletContext;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

@SessionAttributes("location_x")
public class Code {
    private int tailoring_w=50;//拼图的宽
    private int tailoring_h=50;//拼图的高
    private int location_x=0;//随机位置
    private int location_y=0;//随机位置
    private String picName=".png";//图片的名称
    //web下的目录
    private static String imgPath="";
    private static String sourceImgPath="";
    private static final String tempCodeImg="tempCodeImg";
    private static final  String sourceCodeImg="sourceCodeImg"; //web目录下存放的图片
    private  static final int shadowwidth=4;//阴影宽度
    private static final int lightHeightWidth=5;//高光宽度
    private static final int arc=10;//圆弧直径
    public Code(){

    }
    public static  void init(ServletContext context){ //源图片的位置及其生成的临时图位置
        imgPath=context.getRealPath("/")+tempCodeImg; // 当前真实路径+临时位置
        sourceImgPath=context.getRealPath("/")+sourceCodeImg;//源图位置
    }
    //创建图片到临时目录，并返回文件名
    public Map<String,String>  create(ModelMap modelMap , String havingfilename) throws IOException {
        File file=new File(sourceImgPath);//在此目录创建新文件
        String []list=file.list();//list方法返回目录下所有文件名 返回string数组
        String filename;
        //获取随机的图片
        while(true){
            int randowval= RandomUtils.nextInt(0,list.length);//随机产生一个整数
            filename=list[randowval];
            if(!filename.equals(havingfilename)){ //不能获取已有图片
                break;
            }
        }
        File sourceFile=new File(sourceImgPath+File.separator+filename);//根据不同的系统给出不同的分隔符
        Map<String,String> result=createImg(sourceFile,filename);
        modelMap.addAttribute("location_x",location_x);
        return result;
    }
    //裁剪凹槽
    private  static BufferedImage cutImg(File file,int x,int y,int w,int h)throws  IOException{
        Iterator iterator= ImageIO.getImageReadersByFormatName("png");
        //返回包含所有当前已注册 ImageReader 的 Iterator，这些 ImageReader 声称能够 解码 指定格式
        ImageReader reader=(ImageReader)iterator.next();
        ImageInputStream in=ImageIO.createImageInputStream(new FileInputStream(file));//字节输入流对象
        reader.setInput(in,true);//true按升序从此输入源中读取图像和元数据
        BufferedImage bufferedImage;
        try{
            ImageReadParam param=reader.getDefaultReadParam();
            //将字节流转换图像
            Rectangle rect=new Rectangle(x,y,w,h);
            param.setSourceRegion(rect); //将区域描绘成矩形
            bufferedImage=reader.read(0,param);
            //使用所提供的Param 读取通过索引 imageIndex 指定的对象，并将它作为一个完整的 BufferedImage 返回
        }finally {
            if(in!=null) {
                in.close();
            }
        }
        return bufferedImage;
    }
    //获取拼图
    private String createBigImg(BufferedImage smallImage,File file,String filename) throws  IOException{
        String bigImgName=randomImgName("big_"+filename.replaceAll(".png","")+"_");
        File bigfile=new File(imgPath+File.separator+bigImgName);
        if(!bigfile.exists()){
            BufferedImage  bigImg=addWatermark(file,smallImage,0.6F);
            ImageIO.write(bigImg,"png",bigfile);
        }
        return bigImgName;
    }
    private BufferedImage addWatermark(File file,BufferedImage smallImage,float alpha)throws IOException{
        BufferedImage source=ImageIO.read(file);
        Graphics2D graphics2D=source.createGraphics();
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));//为上下文设置composite接口
        //alphaComposite 将源色与目标色组合，在图形和图像中实现混合和透明效果 getinstance参数为规则:在源色之上合成源色中的目标色部分，并将替换目标色
        graphics2D.drawImage(smallImage,location_x,location_y,null);
        graphics2D.dispose();
        return source;
    }
    private String randomImgName(String suf){
        return suf+location_y+"_"+ DigestUtils.md5Hex(String.valueOf(location_x)).substring(0,16)+picName;
        //加密工具类digestUtils
    }

    private Map<String,String> createImg(File file,String filename)throws IOException{
        BufferedImage sourceBuff=ImageIO.read(file); //返回bufferedimage对象 将图片加载到bufferediamge的缓存区以进行操作
        int width=sourceBuff.getWidth();
        int height=sourceBuff.getHeight();
        Random random=new Random();
        this.location_x=random.nextInt(width-tailoring_w*2)+tailoring_w;//凹槽的位置 距离有tailoring_w以上
        this.location_y=random.nextInt(height-tailoring_h);
        BufferedImage sourceSmall=cutImg(file,location_x,location_y,tailoring_w,tailoring_h);//制作凹槽
        List<Shape> shapes=createSmallShape();
        Shape area=shapes.get(0);
        Shape bigarea=shapes.get(1);
        BufferedImage bfm1=new BufferedImage(tailoring_w,tailoring_h,BufferedImage.TYPE_INT_ARGB);
        BufferedImage bfm2=new BufferedImage(tailoring_w,tailoring_h,BufferedImage.TYPE_INT_ARGB);
        for(int i=0;i<tailoring_w;i++){
            for(int j=0;j<tailoring_h;j++){
                if(area.contains(i,j)){
                    bfm1.setRGB(i,j,sourceSmall.getRGB(i,j));
                }
                if(bigarea.contains(i,j)){
                    bfm2.setRGB(i,j,Color.black.getRGB());
                }
            }
        }
        BufferedImage resultImgBuff=deaLightAndShadow(bfm1,area);
        String smallFileName=randomImgName("small_"+filename.replaceAll(".png","")+"_");
        File smallfile=new File(imgPath+File.separator+smallFileName);
        if(!smallfile.exists()){
            ImageIO.write(resultImgBuff,"png",smallfile);
        }
        Map<String,String> result=new HashMap<String, String>();
        result.put("smallImgName",smallFileName);
        String bigImgName=createBigImg(bfm2,new File(sourceImgPath+File.separator+filename),filename);
        result.put("bigImgName",bigImgName);
        result.put("location_y",String.valueOf(location_y));
        result.put("sourceImgName",filename);
        return result;
    }
    //制作凹槽的两个突出
    private java.util.List<Shape> createSmallShape(){
        int face1=RandomUtils.nextInt(0,3); //突出1的方向
        int face2;//突出2
        //两个突出不在一个方向上
        while(true){
            face2=RandomUtils.nextInt(0,3);
            if(face1!=face2){
                break;
            }
        }
        int position1=RandomUtils.nextInt(0,(tailoring_h-arc*2)/2)+(tailoring_h-arc*2)/2;//随机区域
        Shape shape1=createShape(face1,0,position1);
        Shape bigshape1=createShape(face1,2,position1);
        Shape centre=new Rectangle2D.Float(arc,arc,tailoring_w-2*10,tailoring_h-2*10);
        int position2=RandomUtils.nextInt(0,(tailoring_h-arc*2)/2)+(tailoring_h-arc*2)/2;
        Shape shape2=createShape(face2,0,position2);
        Shape bigshape2=createShape(face2,shadowwidth/2,position2);
        Shape bigcentre=new Rectangle2D.Float(10-shadowwidth/2,10-shadowwidth/2,30+shadowwidth,30+shadowwidth);
        Area area=new Area(centre);
        area.add(new Area(shape1));
        area.add(new Area(shape2));
        Area bigarea=new Area(bigcentre);
        bigarea.add(new Area(bigshape1));
        bigarea.add(new Area(bigshape2));
        List<Shape> list=new ArrayList<Shape>();
        list.add(area);
        list.add(bigarea);
        return list;
        }
        private BufferedImage deaLightAndShadow(BufferedImage bfm,Shape shape)throws  IOException{
        BufferedImage buffimg=((Graphics2D) bfm.getGraphics()).getDeviceConfiguration().createCompatibleImage(50, 50, Transparency.TRANSLUCENT);
        Graphics2D graphics2D = buffimg.createGraphics();
        Graphics2D g2 = (Graphics2D) bfm.getGraphics();
        paintBorderGlow(g2,lightHeightWidth, shape);
        paintBorderShadow(graphics2D, shadowwidth, shape);
        graphics2D.drawImage(bfm, 0, 0, null);
        return buffimg;
    }
    private void paintBorderShadow(Graphics2D g2, int shadowWidth, Shape clipShape) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int sw = shadowWidth * 2;
        for (int i = sw; i >= 2; i -= 2) {
            float pct = (float) (sw - i) / (sw - 1);
            //pct<03. 用于去掉阴影边缘白边，  pct>0.8用于去掉过深的色彩， 如果使用Color.lightGray. 可去掉pct>0.8
            if (pct < 0.3 || pct > 0.8) {
                continue;
            }
            g2.setColor(getMixedColor(new Color(54, 54, 54), pct, Color.WHITE, 1.0f - pct));
            g2.setStroke(new BasicStroke(i));
            g2.draw(clipShape);
        }
    }
    private static final Color clrGlowInnerHi = new Color(253, 239, 175, 148);
    private static final Color clrGlowInnerLo = new Color(255, 209, 0);
    private static final Color clrGlowOuterHi = new Color(253, 239, 175, 124);
    private static final Color clrGlowOuterLo = new Color(255, 179, 0);
    public void paintBorderGlow(Graphics2D g2, int glowWidth, Shape clipShape) {
        int gw = glowWidth * 2;
        for (int i = gw; i >= 2; i -= 2) {
            float pct = (float) (gw - i) / (gw - 1);
            Color mixHi = getMixedColor(clrGlowInnerHi, pct, clrGlowOuterHi, 1.0f - pct);
            Color mixLo = getMixedColor(clrGlowInnerLo, pct, clrGlowOuterLo, 1.0f - pct);
            g2.setPaint(new GradientPaint(0.0f, 35 * 0.25f, mixHi, 0.0f, 35, mixLo));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, pct));
            g2.setStroke(new BasicStroke(i));
            g2.draw(clipShape);
        }
    }
    private static Color getMixedColor(Color c1, float pct1, Color c2, float pct2) {
        float[] clr1 = c1.getComponents(null);
        float[] clr2 = c2.getComponents(null);
        for (int i = 0; i < clr1.length; i++) {
            clr1[i] = (clr1[i] * pct1) + (clr2[i] * pct2);
        }
        return new Color(clr1[0], clr1[1], clr1[2], clr1[3]);
    }
    private Shape createShape(int type, int size, int position) {
        Arc2D.Float d; //画弧
        //弧的矩形窗体的左上角X,Y坐标 椭圆总宽度w 高度h 弧的起始角度start extent - 弧的角跨越 type - 弧的闭合类型
        if (type == 0) {
            //上
            d = new Arc2D.Float(position, 5, 10 + size, 10 + size, 0, 190, Arc2D.CHORD);
        } else if (type == 1) {
            //右
            d = new Arc2D.Float(35, position, 10 + size, 10 + size, 270, 190, Arc2D.CHORD);
        } else if (type == 2) {
            //下
            d = new Arc2D.Float(position, 35, 10 + size, 10 + size, 180, 190, Arc2D.CHORD);
        } else if (type == 3) {
            //左
            d = new Arc2D.Float(5, position, 10 + size, 10 + size, 90, 190, Arc2D.CHORD);
        } else {
            d = new Arc2D.Float(5, position, 10 + size, 10 + size, 90, 190, Arc2D.CHORD);
        }
        return d;
    }
}
