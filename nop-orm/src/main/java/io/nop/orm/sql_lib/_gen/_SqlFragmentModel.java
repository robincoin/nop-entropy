package io.nop.orm.sql_lib._gen;

import io.nop.commons.collections.KeyedList; //NOPMD - suppressed UnusedImports - Used for List Prop
import io.nop.core.lang.json.IJsonHandler;



// tell cpd to start ignoring code - CPD-OFF
/**
 * generate from [59:10:0:0]/nop/schema/orm/sql-lib.xdef <p>
 * 用于保存可以被复用的SQL片段，在下面的sql配置中可以通过<sql:fragment id="xx" />这种标签函数来引用SQL片段
 */
@SuppressWarnings({"PMD.UselessOverridingMethod","PMD.UnusedLocalVariable",
    "PMD.UnnecessaryFullyQualifiedName","PMD.EmptyControlStatement"})
public abstract class _SqlFragmentModel extends io.nop.core.resource.component.AbstractComponentModel {
    
    /**
     *  
     * xml name: id
     * 
     */
    private java.lang.String _id ;
    
    /**
     *  
     * xml name: 
     * 
     */
    private io.nop.core.lang.sql.ISqlGenerator _source ;
    
    /**
     * 
     * xml name: id
     *  
     */
    
    public java.lang.String getId(){
      return _id;
    }

    
    public void setId(java.lang.String value){
        checkAllowChange();
        
        this._id = value;
           
    }

    
    /**
     * 
     * xml name: 
     *  
     */
    
    public io.nop.core.lang.sql.ISqlGenerator getSource(){
      return _source;
    }

    
    public void setSource(io.nop.core.lang.sql.ISqlGenerator value){
        checkAllowChange();
        
        this._source = value;
           
    }

    

    public void freeze(boolean cascade){
        if(frozen()) return;
        super.freeze(cascade);

        if(cascade){ //NOPMD - suppressed EmptyControlStatement - Auto Gen Code
        
        }
    }

    protected void outputJson(IJsonHandler out){
        super.outputJson(out);
        
        out.put("id",this.getId());
        out.put("source",this.getSource());
    }
}
 // resume CPD analysis - CPD-ON
