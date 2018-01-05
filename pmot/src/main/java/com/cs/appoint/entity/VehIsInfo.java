package com.cs.appoint.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.cs.mvc.dao.BaseEntity;



@XmlRootElement
public class VehIsInfo extends BaseEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String xh;

    private String hpzl;

    private String hphm;

    private String clpp1;

    private String clxh;

    private String clpp2;

    private String gcjk;

    private String zzg;

    private String zzcmc;

    private String clsbdh;

    private String fdjh;

    private String cllx;

    private String csys;

    private String syxz;

    private String sfzmhm;

    private String sfzmmc;

    private String syr;

    private String ccdjrq;

    private String djrq;

    private String yxqz;

    private String qzbfqz;

    private String fzjg;

    private String glbm;

    private String bxzzrq;

    private String zt;

    private String dybj;

    private String fdjxh;

    private String rlzl;

    private Integer pl;

    private BigDecimal gl;

    private String zxxs;

    private Integer cwkc;

    private Short cwkk;

    private Short cwkg;

    private Integer hxnbcd;

    private Short hxnbkd;

    private Short hxnbgd;

    private Short gbthps;

    private Short zs;

    private Integer zj;

    private Short qlj;

    private Short hlj;

    private String ltgg;

    private Short lts;

    private Integer zzl;

    private Integer zbzl;

    private Integer hdzzl;

    private Short hdzk;

    private Integer zqyzl;

    private Short qpzk;

    private Short hpzk;

    private String hbdbqk;

    private String ccrq;

    private String clyt;

    private String ytsx;

    private String xszbh;

    private String jyhgbzbh;

    private String xzqh;

    private String zsxzqh;

    private String zzxzqh;

    private String sgcssbwqk;

    private String sfmj;

    private String bmjyy;

    private String sfxny;

    private String xnyzl;

    private String bz;

    private Date downloadTime;

    private String stationId;

    private String bookNumber;
    
    private String responseXml;
    
    /** 完整的号牌号码（包括省份） */
    private String fullHphm;


    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public String getHpzl() {
        return hpzl;
    }

    public void setHpzl(String hpzl) {
        this.hpzl = hpzl;
    }

    public String getHphm() {
        return hphm;
    }

    public void setHphm(String hphm) {
        this.hphm = hphm;
    }

    public String getClpp1() {
        return clpp1;
    }

    public void setClpp1(String clpp1) {
        this.clpp1 = clpp1;
    }

    public String getClxh() {
        return clxh;
    }

    public void setClxh(String clxh) {
        this.clxh = clxh;
    }

    public String getClpp2() {
        return clpp2;
    }

    public void setClpp2(String clpp2) {
        this.clpp2 = clpp2;
    }

    public String getGcjk() {
        return gcjk;
    }

    public void setGcjk(String gcjk) {
        this.gcjk = gcjk;
    }

    public String getZzg() {
        return zzg;
    }

    public void setZzg(String zzg) {
        this.zzg = zzg;
    }

    public String getZzcmc() {
        return zzcmc;
    }

    public void setZzcmc(String zzcmc) {
        this.zzcmc = zzcmc;
    }

    public String getClsbdh() {
        return clsbdh;
    }

    public void setClsbdh(String clsbdh) {
        this.clsbdh = clsbdh;
    }

    public String getFdjh() {
        return fdjh;
    }

    public void setFdjh(String fdjh) {
        this.fdjh = fdjh;
    }

    public String getCllx() {
        return cllx;
    }

    public void setCllx(String cllx) {
        this.cllx = cllx;
    }

    public String getCsys() {
        return csys;
    }

    public void setCsys(String csys) {
        this.csys = csys;
    }

    public String getSyxz() {
        return syxz;
    }

    public void setSyxz(String syxz) {
        this.syxz = syxz;
    }

    public String getSfzmhm() {
        return sfzmhm;
    }

    public void setSfzmhm(String sfzmhm) {
        this.sfzmhm = sfzmhm;
    }

    public String getSfzmmc() {
        return sfzmmc;
    }

    public void setSfzmmc(String sfzmmc) {
        this.sfzmmc = sfzmmc;
    }

    public String getSyr() {
        return syr;
    }

    public void setSyr(String syr) {
        this.syr = syr;
    }

    public String getCcdjrq() {
        return ccdjrq;
    }

    public void setCcdjrq(String ccdjrq) {
        this.ccdjrq = ccdjrq;
    }

    public String getDjrq() {
        return djrq;
    }

    public void setDjrq(String djrq) {
        this.djrq = djrq;
    }

    public String getYxqz() {
        return yxqz;
    }

    public void setYxqz(String yxqz) {
        this.yxqz = yxqz;
    }

    public String getQzbfqz() {
        return qzbfqz;
    }

    public void setQzbfqz(String qzbfqz) {
        this.qzbfqz = qzbfqz;
    }

    public String getFzjg() {
        return fzjg;
    }

    public void setFzjg(String fzjg) {
        this.fzjg = fzjg;
    }

    public String getGlbm() {
        return glbm;
    }

    public void setGlbm(String glbm) {
        this.glbm = glbm;
    }

    public String getBxzzrq() {
        return bxzzrq;
    }

    public void setBxzzrq(String bxzzrq) {
        this.bxzzrq = bxzzrq;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public String getDybj() {
        return dybj;
    }

    public void setDybj(String dybj) {
        this.dybj = dybj;
    }

    public String getFdjxh() {
        return fdjxh;
    }

    public void setFdjxh(String fdjxh) {
        this.fdjxh = fdjxh;
    }

    public String getRlzl() {
        return rlzl;
    }

    public void setRlzl(String rlzl) {
        this.rlzl = rlzl;
    }

    public Integer getPl() {
        return pl;
    }

    public void setPl(Integer pl) {
        this.pl = pl;
    }

    public BigDecimal getGl() {
        return gl;
    }

    public void setGl(BigDecimal gl) {
        this.gl = gl;
    }

    public String getZxxs() {
        return zxxs;
    }

    public void setZxxs(String zxxs) {
        this.zxxs = zxxs;
    }

    public Integer getCwkc() {
        return cwkc;
    }

    public void setCwkc(Integer cwkc) {
        this.cwkc = cwkc;
    }

    public Short getCwkk() {
        return cwkk;
    }

    public void setCwkk(Short cwkk) {
        this.cwkk = cwkk;
    }

    public Short getCwkg() {
        return cwkg;
    }

    public void setCwkg(Short cwkg) {
        this.cwkg = cwkg;
    }

    public Integer getHxnbcd() {
        return hxnbcd;
    }

    public void setHxnbcd(Integer hxnbcd) {
        this.hxnbcd = hxnbcd;
    }

    public Short getHxnbkd() {
        return hxnbkd;
    }

    public void setHxnbkd(Short hxnbkd) {
        this.hxnbkd = hxnbkd;
    }

    public Short getHxnbgd() {
        return hxnbgd;
    }

    public void setHxnbgd(Short hxnbgd) {
        this.hxnbgd = hxnbgd;
    }

    public Short getGbthps() {
        return gbthps;
    }

    public void setGbthps(Short gbthps) {
        this.gbthps = gbthps;
    }

    public Short getZs() {
        return zs;
    }

    public void setZs(Short zs) {
        this.zs = zs;
    }

    public Integer getZj() {
        return zj;
    }

    public void setZj(Integer zj) {
        this.zj = zj;
    }

    public Short getQlj() {
        return qlj;
    }

    public void setQlj(Short qlj) {
        this.qlj = qlj;
    }

    public Short getHlj() {
        return hlj;
    }

    public void setHlj(Short hlj) {
        this.hlj = hlj;
    }

    public String getLtgg() {
        return ltgg;
    }

    public void setLtgg(String ltgg) {
        this.ltgg = ltgg;
    }

    public Short getLts() {
        return lts;
    }

    public void setLts(Short lts) {
        this.lts = lts;
    }

    public Integer getZzl() {
        return zzl;
    }

    public void setZzl(Integer zzl) {
        this.zzl = zzl;
    }

    public Integer getZbzl() {
        return zbzl;
    }

    public void setZbzl(Integer zbzl) {
        this.zbzl = zbzl;
    }

    public Integer getHdzzl() {
        return hdzzl;
    }

    public void setHdzzl(Integer hdzzl) {
        this.hdzzl = hdzzl;
    }

    public Short getHdzk() {
        return hdzk;
    }

    public void setHdzk(Short hdzk) {
        this.hdzk = hdzk;
    }

    public Integer getZqyzl() {
        return zqyzl;
    }

    public void setZqyzl(Integer zqyzl) {
        this.zqyzl = zqyzl;
    }

    public Short getQpzk() {
        return qpzk;
    }

    public void setQpzk(Short qpzk) {
        this.qpzk = qpzk;
    }

    public Short getHpzk() {
        return hpzk;
    }

    public void setHpzk(Short hpzk) {
        this.hpzk = hpzk;
    }

    public String getHbdbqk() {
        return hbdbqk;
    }

    public void setHbdbqk(String hbdbqk) {
        this.hbdbqk = hbdbqk;
    }

    public String getCcrq() {
        return ccrq;
    }

    public void setCcrq(String ccrq) {
        this.ccrq = ccrq;
    }

    public String getClyt() {
        return clyt;
    }

    public void setClyt(String clyt) {
        this.clyt = clyt;
    }

    public String getYtsx() {
        return ytsx;
    }

    public void setYtsx(String ytsx) {
        this.ytsx = ytsx;
    }

    public String getXszbh() {
        return xszbh;
    }

    public void setXszbh(String xszbh) {
        this.xszbh = xszbh;
    }

    public String getJyhgbzbh() {
        return jyhgbzbh;
    }

    public void setJyhgbzbh(String jyhgbzbh) {
        this.jyhgbzbh = jyhgbzbh;
    }

    public String getXzqh() {
        return xzqh;
    }

    public void setXzqh(String xzqh) {
        this.xzqh = xzqh;
    }

    public String getZsxzqh() {
        return zsxzqh;
    }

    public void setZsxzqh(String zsxzqh) {
        this.zsxzqh = zsxzqh;
    }

    public String getZzxzqh() {
        return zzxzqh;
    }

    public void setZzxzqh(String zzxzqh) {
        this.zzxzqh = zzxzqh;
    }

    public String getSgcssbwqk() {
        return sgcssbwqk;
    }

    public void setSgcssbwqk(String sgcssbwqk) {
        this.sgcssbwqk = sgcssbwqk;
    }

    public String getSfmj() {
        return sfmj;
    }

    public void setSfmj(String sfmj) {
        this.sfmj = sfmj;
    }

    public String getBmjyy() {
        return bmjyy;
    }

    public void setBmjyy(String bmjyy) {
        this.bmjyy = bmjyy;
    }

    public String getSfxny() {
        return sfxny;
    }

    public void setSfxny(String sfxny) {
        this.sfxny = sfxny;
    }

    public String getXnyzl() {
        return xnyzl;
    }

    public void setXnyzl(String xnyzl) {
        this.xnyzl = xnyzl;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Date getDownloadTime() {
		return downloadTime;
	}

	public void setDownloadTime(Date downloadTime) {
		this.downloadTime = downloadTime;
	}

	public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(String bookNumber) {
        this.bookNumber = bookNumber;
    }

	public String getResponseXml() {
		return responseXml;
	}

	public void setResponseXml(String responseXml) {
		this.responseXml = responseXml;
	}
	public String getFullHphm() {
		return fullHphm;
	}

	public void setFullHphm(String fullHphm) {
		this.fullHphm = fullHphm;
	}
    
}