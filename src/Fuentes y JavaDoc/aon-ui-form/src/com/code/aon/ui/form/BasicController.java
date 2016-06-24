package com.code.aon.ui.form;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;

import org.apache.commons.lang.SerializationUtils;

import com.code.aon.common.ICollectionProvider;
import com.code.aon.common.ILookupObject;
import com.code.aon.common.ITransferObject;
import com.code.aon.common.ManagerBeanException;
import com.code.aon.ql.Criteria;
import com.code.aon.ql.util.ExpressionException;
import com.code.aon.ui.form.event.ControllerEvent;
import com.code.aon.ui.form.event.ControllerListenerException;
import com.code.aon.ui.form.event.ControllerListenerSupport;
import com.code.aon.ui.form.event.IControllerListener;

/**
 * Controller for Basic Structures.
 * 
 * @author Consulting & Development. I�aki Ayerbe - 06-abr-2005
 */
public class BasicController extends AbstractPojoController implements IController, ICollectionProvider {

    private static final Logger LOGGER = Logger.getLogger(BasicController.class.getName());

    private Criteria criteria = new Criteria();

    private ITransferObject to;

    /** Represent the model of data that we are going to interact with */
    protected DataModel model;

    private boolean isNew;

    private boolean queryOnStartUP;

    /** Represent a manager of listeners */
    protected ControllerListenerSupport controllerListenerSupport;

    private int pageLimit = PageDataModel.LIMIT;

    private int selectedIndex;

    private List<IControllerListener> listenerClasses;
    
    private boolean saveState;
    
    private Serializable savedToId;

    /**
     * Constructor.
     * 
     */
    public BasicController() {
        this.controllerListenerSupport = new ControllerListenerSupport();
        this.selectedIndex = -1;
        this.saveState = true;
    }

    /**
     * Return if a query will be executed on the model associated to controller when starting up. 
     * 
     * @return queryOnStartUP
     */
    public boolean isQueryOnStartUP() {
        return queryOnStartUP;
    }

    /**
     * Set if a query will be executed on the model associated to controller when starting up.
     * 
     * @param queryOnStartUP
     */
    public void setQueryOnStartUP(boolean queryOnStartUP) {
        this.queryOnStartUP = queryOnStartUP;
    }

    /**
     * Return the limit of page in the model associated to controller.
     * 
     * @return int
     */
    public int getPageLimit() {
        return pageLimit;
    }

    /**
     * Set the limit of page in the model associated to controller.
     * 
     * @param pageLimit
     */
    public void setPageLimit(int pageLimit) {
        this.pageLimit = pageLimit;
    }

    /**
     * Return a list containing the listeners associated to controller.
     * 
     * @return List<IControllerListener>
     */
    public List<IControllerListener> getListenerClasses() {
        return listenerClasses;
    }

    /**
     * Set a list containing the listeners associated to controller.
     * 
     * @param listenerClasses
     */
    public void setListenerClasses(List<IControllerListener> listenerClasses) {
        this.listenerClasses = listenerClasses;
        addListeners();
    }

    /* (non-Javadoc)
     * @see com.code.aon.ui.form.IController#getModel()
     */
    public DataModel getModel() throws ManagerBeanException {
        if (model == null) {
            if (isQueryOnStartUP()) {
                initializeModel();
            } else {
                this.model = new PageDataModel();
            }
        }
        return model;
    }

    /* (non-Javadoc)
     * @see com.code.aon.ui.form.IController#setModel(javax.faces.model.DataModel)
     */
    public void setModel(DataModel model) {
        this.model = model;
    }

    /**
     * Return an object representing the data for the currently selected row index of the model associated to controller.
     * 
     * @return Object
     */
    protected Object getSelectedTO() {
        return this.model.getRowData();
    }

    /**
     * Return the zero-relative index of the currently selected row of the model associated to controller. 
     * 
     * @return int
     */
    protected int getSelectedTOIndex() {
        return this.model.getRowIndex();
    }

    /**
     * Return the zero-relative index of the currently selected row of the model associated to controller.
     * 
     * @return int
     */
    public int getSelectedIndex() {
        return this.selectedIndex;
    }

    /* (non-Javadoc)
     * @see com.code.aon.ui.form.IController#isNew()
     */
    public boolean isNew() {
        return isNew;
    }

    /* (non-Javadoc)
     * @see com.code.aon.ui.form.IController#setNew(boolean)
     */
    public void setNew(boolean isNew) {
        if (isNew) {
            this.selectedIndex = -1;
            if(this.model != null){
            	this.model.setRowIndex(this.selectedIndex);
            }
        }
        this.isNew = isNew;
    }
    
    /**
     * Return the state of bean. True if bean is positioned in the first
     * Transfer Object, otherwise false.
     * 
     * @return boolean
     */
    public boolean isInFirst() {
		return getSelectedIndex() == 0;
	}

    /**
     * Return the state of bean. True if bean is positioned in the last
     * Transfer Object, otherwise false.
     * 
     * @return boolean
     * @throws ManagerBeanException 
     */
	public boolean isInLast() throws ManagerBeanException {
		int count = getModel().getRowCount();
		return ( count > 0) && ( (count-1) == getSelectedIndex() );
	}

	/* (non-Javadoc)
     * @see com.code.aon.ui.form.IController#onAccept(javax.faces.event.ActionEvent)
     */
    public void onAccept(ActionEvent event) {
        accept(event);
        resetTo();
    }

    /**
     * Action to insert or to update the current row depending on the value of the variable isNew.
     * 
     * @param event
     */
    @SuppressWarnings("unused")
    public void accept(ActionEvent event) {
        try {
            ControllerEvent evt = new ControllerEvent(this);
            if (isNew) {
                controllerListenerSupport.fireBeforeBeanAdded(evt);
                this.to = add();
                initializeModel();
                setNew(false);
                controllerListenerSupport.fireAfterBeanAdded(evt);
            } else {
                controllerListenerSupport.fireBeforeBeanUpdated(evt);
                to = update();
                controllerListenerSupport.fireAfterBeanUpdated(evt);
            }
        } catch (ControllerListenerException e) {
            LOGGER.severe(">>>> onAccept " + e.getMessage());
            addMessage(e.getMessage());
            throw new AbortProcessingException(e.getMessage(), e);
        } catch (ManagerBeanException e) {
            LOGGER.severe(">>>> onAccept " + e.getMessage());
            addMessage(e.getMessage());
            throw new AbortProcessingException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see com.code.aon.ui.form.IController#onSearch(javax.faces.event.ActionEvent)
     */
    public void onSearch(ActionEvent event) {
        try {
            ControllerEvent evt = new ControllerEvent(this);
            controllerListenerSupport.fireBeforeBeanReset(evt);
            initializeModel();
            resetTo();
            controllerListenerSupport.fireAfterBeanReset(evt);
        } catch (ControllerListenerException e) {
            LOGGER.severe(">>>> onSearch " + e.getMessage());
            addMessage(e.getMessage());
            throw new AbortProcessingException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see com.code.aon.ui.form.IController#onRemove(javax.faces.event.ActionEvent)
     */
    public void onRemove(ActionEvent event) {
        remove(event);
        resetTo();
    }

    /**
     * Action to remove the current row.
     * 
     * @param event
     */
    @SuppressWarnings("unused")
    public void remove(ActionEvent event) {
        try {
            ControllerEvent evt = new ControllerEvent(this);
            controllerListenerSupport.fireBeforeBeanRemoved(evt);
            remove();
            initializeModel();
            controllerListenerSupport.fireAfterBeanRemoved(evt);
        } catch (ControllerListenerException e) {
            LOGGER.severe(">>>> onRemove exception[" + e.getMessage() + "]");
            addMessage(e.getMessage());
            throw new AbortProcessingException(e.getMessage(), e);
        } catch (ManagerBeanException e) {
            LOGGER.severe(">>>> onRemove exception[" + e.getMessage() + "]");
            addMessage(e.getMessage());
            throw new AbortProcessingException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see com.code.aon.ui.form.IController#onCancel(javax.faces.event.ActionEvent)
     */
    public void onCancel(ActionEvent event) {
        try {
        	restoreState();
            ControllerEvent evt = new ControllerEvent(this);
            controllerListenerSupport.fireBeforeBeanCanceled(evt);
            resetTo();
            controllerListenerSupport.fireAfterBeanCanceled(evt);
        } catch (ControllerListenerException e) {
            LOGGER.severe(">>>> onCancel " + e.getMessage());
            addMessage(e.getMessage());
            throw new AbortProcessingException(e.getMessage(), e);
        } catch (ManagerBeanException e) {
            LOGGER.severe(">>>> onReset " + e.getMessage());
            addMessage(e.getMessage());
            throw new AbortProcessingException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see com.code.aon.ui.form.IController#onReset(javax.faces.event.ActionEvent)
     */
    public void onReset(ActionEvent event) {
        try {
            ControllerEvent evt = new ControllerEvent(this);
            setTo(createNewTo());
            controllerListenerSupport.fireBeforeBeanCreated(evt);
            setNew(true);
            controllerListenerSupport.fireAfterBeanCreated(evt);
        } catch (ControllerListenerException e) {
            LOGGER.severe(">>>> onReset " + e.getMessage());
            addMessage(e.getMessage());
            throw new AbortProcessingException(e.getMessage(), e);
        } catch (ManagerBeanException e) {
            LOGGER.severe(">>>> onReset " + e.getMessage());
            addMessage(e.getMessage());
            throw new AbortProcessingException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see com.code.aon.ui.form.IController#onEditSearch(javax.faces.event.ActionEvent)
     */
    public void onEditSearch(ActionEvent event) {
        try {
            ControllerEvent evt = new ControllerEvent(this);
            controllerListenerSupport.fireBeforeBeanReset(evt);
            clearCriteria();
            setTo(createNewTo());
            controllerListenerSupport.fireAfterBeanReset(evt);
        } catch (ControllerListenerException e) {
            LOGGER.severe(">>>> onEditSearch " + e.getMessage());
            addMessage(e.getMessage());
            throw new AbortProcessingException(e.getMessage(), e);
        } catch (ManagerBeanException e) {
            LOGGER.severe(">>>> onEditSearch " + e.getMessage());
            addMessage(e.getMessage());
            throw new AbortProcessingException(e.getMessage(), e);
        }
    }

    /**
     * Execute selection action of the first element of the data model.
     * 
     * @param event
     */
    public void onSelectFirst(ActionEvent event) {
    	if (! isInFirst() ) {
            try {
        		getModel().setRowIndex( 0 );
            } catch (ManagerBeanException e) {
                LOGGER.severe(">>>> onSelectFirst exception:[" + e.getMessage() + "]");
                addMessage(e.getMessage());
                throw new AbortProcessingException(e.getMessage(), e);
            }
    		onSelect(event);            
    	}
    }

    /**
     * Execute selection action of the previous element of the data model.
     * 
     * @param event
     */
    public void onSelectPrevious(ActionEvent event) {
    	if ( getSelectedIndex() > 0 ) {
            try {
        		getModel().setRowIndex( getSelectedIndex()-1 );
            } catch (ManagerBeanException e) {
                LOGGER.severe(">>>> onSelectFirst exception:[" + e.getMessage() + "]");
                addMessage(e.getMessage());
                throw new AbortProcessingException(e.getMessage(), e);
            }
    		onSelect(event);            
    	}
    }

    /**
     * Execute selection action of the next element of the data model.
     * 
     * @param event
     */
    public void onSelectNext(ActionEvent event) {
        try {
        	DataModel model = getModel();
        	int index = getSelectedIndex();
        	if ( (index != -1) && (index < (model.getRowCount()-1)) ) {
        		getModel().setRowIndex( index+1 );
        		onSelect(event);
        	}
        } catch (ManagerBeanException e) {
            LOGGER.severe(">>>> onSelectFirst exception:[" + e.getMessage() + "]");
            addMessage(e.getMessage());
            throw new AbortProcessingException(e.getMessage(), e);
        }
    }

    /**
     * Execute selection action of the last element of the data model.
     * 
     * @param event
     */
    public void onSelectLast(ActionEvent event) {
    	try {
        	if ( (getModel().getRowCount() > 0) && (! isInLast()) ) {
        		getModel().setRowIndex( getModel().getRowCount()-1 );
        		onSelect(event);        		
        	}
        } catch (ManagerBeanException e) {
            LOGGER.severe(">>>> onSelectFirst exception:[" + e.getMessage() + "]");
            addMessage(e.getMessage());
            throw new AbortProcessingException(e.getMessage(), e);
        }
    }
    
    
    /* (non-Javadoc)
     * @see com.code.aon.ui.form.IController#onSelect(javax.faces.event.ActionEvent)
     */
    public void onSelect(ActionEvent event) {
        try {
            ControllerEvent evt = new ControllerEvent(this);
            controllerListenerSupport.fireBeforeBeanSelected(evt);
            selectedIndex = getSelectedTOIndex();
            LOGGER.fine(">>>> onSelect rowIndex:[" + selectedIndex + "]");
            ITransferObject to = (ITransferObject) getSelectedTO();
            initializePOJO(to);
            setTo(to);
            setNew(false);
            controllerListenerSupport.fireAfterBeanSelected(evt);
            saveState(to);
        } catch (ControllerListenerException e) {
            LOGGER.severe(">>>> onSelect exception:[" + e.getMessage() + "]");
            addMessage(e.getMessage());
            throw new AbortProcessingException(e.getMessage(), e);
        } catch (ManagerBeanException e) {
            LOGGER.severe(">>>> onSelect exception:[" + e.getMessage() + "]");
            addMessage(e.getMessage());
            throw new AbortProcessingException(e.getMessage(), e);
        }
    }
    
	/* (non-Javadoc)
     * @see com.code.aon.ui.form.IController#getTo()
     */
    public ITransferObject getTo() {
        return this.to;
    }

    /* (non-Javadoc)
     * @see com.code.aon.ui.form.ISearchable#addExpression(javax.faces.event.ValueChangeEvent)
     */
    public void addExpression(ValueChangeEvent event) throws ManagerBeanException {
        if (event.getNewValue() != null) {
            String value = event.getNewValue().toString();
            addExpression(getFieldName(event.getComponent().getId()), value);
        }
    }

    /* (non-Javadoc)
     * @see com.code.aon.ui.form.ISearchable#clearCriteria()
     */
    public void clearCriteria() throws ManagerBeanException {
        this.criteria = new Criteria();
    }

    /* (non-Javadoc)
     * @see com.code.aon.common.ICriteriaProvider#getCriteria()
     */
    public Criteria getCriteria() throws ManagerBeanException {
        return criteria;
    }

    /* (non-Javadoc)
     * @see com.code.aon.ui.form.ISearchable#setCriteria(com.code.aon.ql.Criteria)
     */
    public void setCriteria(Criteria criteria) throws ManagerBeanException {
        this.criteria = criteria;
    }

    /**
     * Add a new expression to the criteria to condition the following searches.
     * 
     * @param key
     * @param value
     * @throws ManagerBeanException
     */
    public void addExpression(String key, String value) throws ManagerBeanException {
        try {
            if (value.length() > 0) {
                criteria.addExpression(key, value);
            }
        } catch (ExpressionException e) {
            throw new ManagerBeanException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see com.code.aon.ui.form.IController#initializeModel()
     */
    @SuppressWarnings("unchecked")
    public void initializeModel() {
        try {
            ControllerEvent evt = new ControllerEvent(this);
            controllerListenerSupport.fireBeforeModelInitialized(evt);
            LOGGER.fine(">>>> before InitializeModel");
            if (model == null) {
                int count = getManagerBean().getCount(getCriteria());
                model = new PageDataModel(this, count, getPageLimit());
            } else {
                PageDataModel pdm = (PageDataModel) model;
                pdm.setWrappedData(search(0, getPageLimit()));
                pdm.resize(getManagerBean().getCount(getCriteria()));
            }
            selectedIndex = -1;
            LOGGER.fine("initializeModel RowCount[" + model.getRowCount() + "]");
            controllerListenerSupport.fireAfterModelInitialized(evt);
        } catch (ControllerListenerException e) {
            LOGGER.severe(">>>> initializeModel " + e.getMessage());
            addMessage(e.getMessage());
            throw new AbortProcessingException(e.getMessage(), e);
        } catch (ManagerBeanException e) {
            LOGGER.severe(">>>> initializeModel " + e.getMessage());
            addMessage(e.getMessage());
            try {
                clearCriteria();
            } catch (ManagerBeanException e1) {
                LOGGER.severe(">>>> Unable to clear crtieria! " + e.getMessage());
                addMessage(e.getMessage());
            }
            throw new AbortProcessingException(e.getMessage(), e);
        }

    }

    /**
     * Set the current <code>ITransferObject</code> of the <code>DataModel</code> to the controller.
     *
     * @param value
     */
    protected void setTo(ITransferObject value) {
        this.to = value;
    }

    /**
     * Reset the <code>ITransferObject</code> value.
     * 
     */
    protected void resetTo() {
        this.to = null;
        setNew(false);
    }

    /**
     * Add current <code>ITransferObject</code>.
     * 
     * @return ITransferObject
     * @throws ManagerBeanException
     */
    protected ITransferObject add() throws ManagerBeanException {
        LOGGER.fine("Adding Id:[" + getTo() + "]");
        return getManagerBean().insert(getTo());
    }

    /**
     * Update current <code>ITransferObject</code>.
     *
     * @return ITransferObject
     * @throws ManagerBeanException
     */
    protected ITransferObject update() throws ManagerBeanException {
        LOGGER.fine("Setting Id:[" + getTo() + "]");
        return getManagerBean().update(getTo());
    }

    /**
     * Remove current <code>ITransferObject</code>.
     * 
     * @throws ManagerBeanException
     */
    protected void remove() throws ManagerBeanException {
        LOGGER.fine("Removing Id:[" + getTo() + "]");
        getManagerBean().remove(getTo());
    }

    /* (non-Javadoc)
     * @see com.code.aon.ui.form.ISearchable#search(int, int)
     */
    public List<ITransferObject> search(int start, int count) throws ManagerBeanException {
        Criteria criteria = getCriteria();
        LOGGER.info("Searching Expression:[" + ((criteria != null) ? criteria.toString() : null) + ", " + start + ", " + count
                + "]");
        List<ITransferObject> list = getManagerBean().getList(criteria, start, count);
        return list;
    }

    /**
     * Add listener to controller.
     * 
     * @param listener
     */
    public void addControllerListener(IControllerListener listener) {
        LOGGER.info("Listener registered " + listener);
        controllerListenerSupport.addControllerListener(listener);
    }

    /**
     * Remove listener from controller.
     * 
     * @param listener
     */
    public void removeControllerListener(IControllerListener listener) {
        LOGGER.info("Listener removed " + listener);
        controllerListenerSupport.removeControllerListener(listener);
    }

    /**
     * Return lookups in XML Format.
     * 
     * @return String
     */
    public String getLookupsAsXML() {
        this.selectedIndex = getSelectedTOIndex();
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String ids = (String) ec.getRequestParameterMap().get("ids");
        LOGGER.fine(">>>> getLookups rowIndex:[" + selectedIndex + "] ids:[" + ids + "]");
        return getLookupsAsXML((ILookupObject) getSelectedTO(), ids);
    }

    /**
     * Add all listeners from variable listenerClasses to controller.
     * 
     */
    private void addListeners() {
        Iterator<IControllerListener> iter = listenerClasses.iterator();
        while (iter.hasNext()) {
            IControllerListener listener = iter.next();
            listener.setController(this);
            this.addControllerListener(listener);
        }
    }

    /**
     * Get a collection that contains current <code>ITransferObject</code> associated to controller.
     *  To use in reports.
     *  
     *  @return Collection
     */
    public Collection getCollection() {
        if (this.getTo() != null) {
            List<ITransferObject> l = new LinkedList<ITransferObject>();
            l.add(getTo());
            return l;
        }
        return null;
    }

    /**
     * Get the <code>List<ITransferObject></code> wrapped by the model associated to controller.
     * 
     * @return List<ITransferObject>
     */
    @SuppressWarnings("unchecked")
    public List<ITransferObject> getWrappedList() {
        if (this.model != null) {
            return (List<ITransferObject>) this.model.getWrappedData();
        }
        return Collections.emptyList();
    }
    
    /**
     * Sets the save state.
     * 
     * @param value the value
     */
    public void setSaveState( boolean value ) {
    	this.saveState = value;
    }

    /**
     * Save state.
     * 
     * @param to the to
     */
    protected void saveState( ITransferObject to ) {
    	if ( this.saveState ) {
			try {
				Serializable id = getManagerBean().getId(to);
				this.savedToId = (Serializable) SerializationUtils.clone( id );
			} catch (Throwable e) {
		        LOGGER.severe(">>>> saveState " + e.getMessage());
		        addMessage(e.getMessage());
		        throw new AbortProcessingException(e.getMessage(), e);	        
			}
    	}
    }

    private void restoreState() throws ManagerBeanException {
    	if ( this.saveState && (this.savedToId != null) ) {
   			List<ITransferObject> list = getWrappedList();
    		ITransferObject to = getManagerBean().get( this.savedToId );   			
   			list.set( getSelectedIndex(), to );
    		this.savedToId = null;
    	}
    }
    
}
