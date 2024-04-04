package io.nop.task.model._gen;

import io.nop.commons.collections.KeyedList; //NOPMD NOSONAR - suppressed UnusedImports - Used for List Prop
import io.nop.core.lang.json.IJsonHandler;
import io.nop.task.model.ForkNTaskStepModel;
import io.nop.commons.util.ClassHelper;



// tell cpd to start ignoring code - CPD-OFF
/**
 * generate from [200:14:0:0]/nop/schema/task/task.xdef <p>
 * 
 */
@SuppressWarnings({"PMD.UselessOverridingMethod","PMD.UnusedLocalVariable",
    "PMD.UnnecessaryFullyQualifiedName","PMD.EmptyControlStatement","java:S116","java:S101","java:S1128","java:S1161"})
public abstract class _ForkNTaskStepModel extends io.nop.task.model.TaskStepsModel {
    
    /**
     *  
     * xml name: aggregateVarName
     * 
     */
    private java.lang.String _aggregateVarName ;
    
    /**
     *  
     * xml name: aggregator
     * 对并行步骤执行结果进行汇总处理
     */
    private io.nop.core.lang.eval.IEvalAction _aggregator ;
    
    /**
     *  
     * xml name: autoCancelUnfinished
     * 
     */
    private boolean _autoCancelUnfinished  = true;
    
    /**
     *  
     * xml name: countExpr
     * 
     */
    private io.nop.core.lang.eval.IEvalAction _countExpr ;
    
    /**
     *  
     * xml name: indexName
     * 
     */
    private java.lang.String _indexName ;
    
    /**
     *  
     * xml name: joinType
     * 
     */
    private io.nop.commons.concurrent.AsyncJoinType _joinType ;
    
    /**
     * 
     * xml name: aggregateVarName
     *  
     */
    
    public java.lang.String getAggregateVarName(){
      return _aggregateVarName;
    }

    
    public void setAggregateVarName(java.lang.String value){
        checkAllowChange();
        
        this._aggregateVarName = value;
           
    }

    
    /**
     * 
     * xml name: aggregator
     *  对并行步骤执行结果进行汇总处理
     */
    
    public io.nop.core.lang.eval.IEvalAction getAggregator(){
      return _aggregator;
    }

    
    public void setAggregator(io.nop.core.lang.eval.IEvalAction value){
        checkAllowChange();
        
        this._aggregator = value;
           
    }

    
    /**
     * 
     * xml name: autoCancelUnfinished
     *  
     */
    
    public boolean isAutoCancelUnfinished(){
      return _autoCancelUnfinished;
    }

    
    public void setAutoCancelUnfinished(boolean value){
        checkAllowChange();
        
        this._autoCancelUnfinished = value;
           
    }

    
    /**
     * 
     * xml name: countExpr
     *  
     */
    
    public io.nop.core.lang.eval.IEvalAction getCountExpr(){
      return _countExpr;
    }

    
    public void setCountExpr(io.nop.core.lang.eval.IEvalAction value){
        checkAllowChange();
        
        this._countExpr = value;
           
    }

    
    /**
     * 
     * xml name: indexName
     *  
     */
    
    public java.lang.String getIndexName(){
      return _indexName;
    }

    
    public void setIndexName(java.lang.String value){
        checkAllowChange();
        
        this._indexName = value;
           
    }

    
    /**
     * 
     * xml name: joinType
     *  
     */
    
    public io.nop.commons.concurrent.AsyncJoinType getJoinType(){
      return _joinType;
    }

    
    public void setJoinType(io.nop.commons.concurrent.AsyncJoinType value){
        checkAllowChange();
        
        this._joinType = value;
           
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
        
        out.putNotNull("aggregateVarName",this.getAggregateVarName());
        out.putNotNull("aggregator",this.getAggregator());
        out.putNotNull("autoCancelUnfinished",this.isAutoCancelUnfinished());
        out.putNotNull("countExpr",this.getCountExpr());
        out.putNotNull("indexName",this.getIndexName());
        out.putNotNull("joinType",this.getJoinType());
    }

    public ForkNTaskStepModel cloneInstance(){
        ForkNTaskStepModel instance = newInstance();
        this.copyTo(instance);
        return instance;
    }

    protected void copyTo(ForkNTaskStepModel instance){
        super.copyTo(instance);
        
        instance.setAggregateVarName(this.getAggregateVarName());
        instance.setAggregator(this.getAggregator());
        instance.setAutoCancelUnfinished(this.isAutoCancelUnfinished());
        instance.setCountExpr(this.getCountExpr());
        instance.setIndexName(this.getIndexName());
        instance.setJoinType(this.getJoinType());
    }

    protected ForkNTaskStepModel newInstance(){
        return (ForkNTaskStepModel) ClassHelper.newInstance(getClass());
    }
}
 // resume CPD analysis - CPD-ON
