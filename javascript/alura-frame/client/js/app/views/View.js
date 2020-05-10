class View {

    constructor(elemento){
        this._elemento = elemento;
    }

    template(model){
        throw new Error('The template method should be overwritten');
    }

    update(model){
        this._elemento.innerHTML = this.template(model);
    }

}