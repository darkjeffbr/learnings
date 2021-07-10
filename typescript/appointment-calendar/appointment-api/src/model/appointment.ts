export interface Appointment {
    title: string;
    notes: string[];
    allDay: boolean;
    start: Date;
    end: Date;
    date: Date;
}