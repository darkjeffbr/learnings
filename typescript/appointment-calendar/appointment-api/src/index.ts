import 'dotenv/config';

import express, { Application } from "express";
import {routes} from "./routes"
import { Mongoose } from "mongoose";

const app: Application = express();
const port = process.env.SERVER_PORT;

app.use(express.json());
app.use('/api/v1/', routes);

const DB_CONNECTION_STRING = `mongodb://${process.env.DB_USERNAME}:${process.env.DB_PASSWD}@${process.env.DB_SERVER}:${process.env.DB_PORT}/${process.env.DB_NAME}?authSource=admin`;

const mongoose = new Mongoose({ useNewUrlParser: true,  useUnifiedTopology: true});

mongoose.connect(`${DB_CONNECTION_STRING}`).then(() => {
    console.log('Connected successfully to database');
    console.log('Starting up server ...');
    try {
        app.listen(port, (): void => {
            console.log(`Server listening on port ${port}`);
        });
    } catch (error) {
        console.error(`Error occured: ${error.message}`);
    }
}).catch(error => {
    console.error(`Error occured: ${error}`);
})


/*

const URL = `mongodb://${process.env.DB_USERNAME}:${process.env.DB_PASSWD}@${process.env.DB_SERVER}:${process.env.DB_PORT}/appointments?authSource=admin`;

mongoose.connect(URL).then(()=> {
    console.log(mongoose.connection.readyState);

    interface User {
        name: string;
    }
    
    const UserSchema = new Schema<User>({
        name: { type: String, required: true }
    });

    const User = mongoose.model<User>('Kittenx', UserSchema);
    const fluffy = new User({name: 'fluffy4'});
    
    fluffy.save((err: any, fluffy: any)=>{
        if(err) console.error(err);
    });
});

*/