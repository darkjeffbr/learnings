import { Appointment } from "../model/appointment";
import { CrudRepository } from "./crud-repository";
import { AppointmentModel } from "./model/appointment-model"

export class AppointmentRepository extends CrudRepository<number, Appointment> {

    count(): number {
        return 0; //this._db.size;
    }

    deleteById(id: number): void {
        //this._db.delete(id)
    }

    existsById(id: number): boolean {
        return false; //this._db.has(id);
    }

    findAll(): Appointment[] {
        const all = AppointmentModel.find().exec();
        console.log(all);
        return []; //[...this._db.values()];
    }

    findById(id: number): Appointment {
        return null; //this._db.get(id) as Appointment;
    }

    save(entity: Appointment): Appointment {
        /*
        if(!entity.id){
            entity.id = this._db.size+1;
        }else if( this.existsById(entity.id) ) {
            throw new Error('Entity with specified ID already exists');
        }
        this._db.set(entity.id, entity);
        */
        
        /*
        const fluffy = new User({name: 'fluffy4'});
    
    fluffy.save((err: any, fluffy: any)=>{
        if(err) console.error(err);
    });
        */
        const appointment = new AppointmentModel(entity);
        appointment.save();
        return entity;
    }

    update(id: number, entity: Appointment): Appointment {
        // this._db.set(id, entity);
        return entity;
    }

}