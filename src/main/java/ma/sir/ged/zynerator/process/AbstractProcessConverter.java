package ma.sir.ged.zynerator.process;



import ma.sir.ged.zynerator.audit.AuditBusinessObject;

public abstract class AbstractProcessConverter<I extends AbstractProcessInput, O extends AbstractProcessOutput, T extends AuditBusinessObject> {

    protected Class<T> itemType;
    protected Class<I> inputType;
    protected Class<O> outputType;

    public AbstractProcessConverter(Class<T> itemType, Class<I> inputType, Class<O> outputType) {
        this.itemType = itemType;
        this.inputType = inputType;
        this.outputType = outputType;
    }

    public abstract T toItem(I input);

    public abstract O toOutput(T item);


    }
