package io.nop.task.model._gen;

import io.nop.commons.collections.KeyedList; //NOPMD NOSONAR - suppressed UnusedImports - Used for List Prop
import io.nop.core.lang.json.IJsonHandler;
import io.nop.task.model.TaskInputModel;
import io.nop.commons.util.ClassHelper;



// tell cpd to start ignoring code - CPD-OFF
/**
 * generate from [27:10:0:0]/nop/schema/task/task.xdef <p>
 * 
 */
@SuppressWarnings({"PMD.UselessOverridingMethod","PMD.UnusedLocalVariable",
    "PMD.UnnecessaryFullyQualifiedName","PMD.EmptyControlStatement","java:S116","java:S101","java:S1128","java:S1161"})
public abstract class _TaskInputModel extends io.nop.core.resource.component.AbstractComponentModel {
    
    /**
     *  
     * xml name: description
     * 
     */
    private java.lang.String _description ;
    
    /**
     *  
     * xml name: displayName
     * 
     */
    private java.lang.String _displayName ;
    
    /**
     *  
     * xml name: fromTaskScope
     * 
     */
    private boolean _fromTaskScope  = false;
    
    /**
     *  
     * xml name: mandatory
     * 
     */
    private boolean _mandatory  = false;
    
    /**
     *  
     * xml name: name
     * 
     */
    private java.lang.String _name ;
    
    /**
     *  是否持久化保存
     * xml name: persist
     * 标记为persist的变量会自动保存，支持中断后恢复执行
     */
    private boolean _persist  = true;
    
    /**
     *  
     * xml name: role
     * 
     */
    private java.lang.String _role ;
    
    /**
     *  
     * xml name: source
     * 
     */
    private io.nop.core.lang.eval.IEvalAction _source ;
    
    /**
     *  
     * xml name: type
     * 
     */
    private io.nop.core.type.IGenericType _type ;
    
    /**
     * 
     * xml name: description
     *  
     */
    
    public java.lang.String getDescription(){
      return _description;
    }

    
    public void setDescription(java.lang.String value){
        checkAllowChange();
        
        this._description = value;
           
    }

    
    /**
     * 
     * xml name: displayName
     *  
     */
    
    public java.lang.String getDisplayName(){
      return _displayName;
    }

    
    public void setDisplayName(java.lang.String value){
        checkAllowChange();
        
        this._displayName = value;
           
    }

    
    /**
     * 
     * xml name: fromTaskScope
     *  
     */
    
    public boolean isFromTaskScope(){
      return _fromTaskScope;
    }

    
    public void setFromTaskScope(boolean value){
        checkAllowChange();
        
        this._fromTaskScope = value;
           
    }

    
    /**
     * 
     * xml name: mandatory
     *  
     */
    
    public boolean isMandatory(){
      return _mandatory;
    }

    
    public void setMandatory(boolean value){
        checkAllowChange();
        
        this._mandatory = value;
           
    }

    
    /**
     * 
     * xml name: name
     *  
     */
    
    public java.lang.String getName(){
      return _name;
    }

    
    public void setName(java.lang.String value){
        checkAllowChange();
        
        this._name = value;
           
    }

    
    /**
     * 是否持久化保存
     * xml name: persist
     *  标记为persist的变量会自动保存，支持中断后恢复执行
     */
    
    public boolean isPersist(){
      return _persist;
    }

    
    public void setPersist(boolean value){
        checkAllowChange();
        
        this._persist = value;
           
    }

    
    /**
     * 
     * xml name: role
     *  
     */
    
    public java.lang.String getRole(){
      return _role;
    }

    
    public void setRole(java.lang.String value){
        checkAllowChange();
        
        this._role = value;
           
    }

    
    /**
     * 
     * xml name: source
     *  
     */
    
    public io.nop.core.lang.eval.IEvalAction getSource(){
      return _source;
    }

    
    public void setSource(io.nop.core.lang.eval.IEvalAction value){
        checkAllowChange();
        
        this._source = value;
           
    }

    
    /**
     * 
     * xml name: type
     *  
     */
    
    public io.nop.core.type.IGenericType getType(){
      return _type;
    }

    
    public void setType(io.nop.core.type.IGenericType value){
        checkAllowChange();
        
        this._type = value;
           
    }

    

    @Override
    public void freeze(boolean cascade){
        if(frozen()) return;
        super.freeze(cascade);

        if(cascade){ //NOPMD - suppressed EmptyControlStatement - Auto Gen Code
        
        }
    }

    @Override
    protected void outputJson(IJsonHandler out){
        super.outputJson(out);
        
        out.putNotNull("description",this.getDescription());
        out.putNotNull("displayName",this.getDisplayName());
        out.putNotNull("fromTaskScope",this.isFromTaskScope());
        out.putNotNull("mandatory",this.isMandatory());
        out.putNotNull("name",this.getName());
        out.putNotNull("persist",this.isPersist());
        out.putNotNull("role",this.getRole());
        out.putNotNull("source",this.getSource());
        out.putNotNull("type",this.getType());
    }

    public TaskInputModel cloneInstance(){
        TaskInputModel instance = newInstance();
        this.copyTo(instance);
        return instance;
    }

    protected void copyTo(TaskInputModel instance){
        super.copyTo(instance);
        
        instance.setDescription(this.getDescription());
        instance.setDisplayName(this.getDisplayName());
        instance.setFromTaskScope(this.isFromTaskScope());
        instance.setMandatory(this.isMandatory());
        instance.setName(this.getName());
        instance.setPersist(this.isPersist());
        instance.setRole(this.getRole());
        instance.setSource(this.getSource());
        instance.setType(this.getType());
    }

    protected TaskInputModel newInstance(){
        return (TaskInputModel) ClassHelper.newInstance(getClass());
    }
}
 // resume CPD analysis - CPD-ON
