import RecurringScheduleElement from '../dto/recurring-schedule-element';
import { dayOfWeekToLongString, dayOfWeekToShortString } from './day-of-week-to-string-mapper';

const dateToHour = (date: Date) => {
    return new Date(Date.parse(date.toString())).toLocaleTimeString().slice(0, -3);
}

const recurringScheduleElementToLongDateString = (rse: RecurringScheduleElement) => {
    const dayOfWeek = dayOfWeekToLongString(rse.dayOfWeek);
    const hours = `${dateToHour(rse.startDate)} - ${dateToHour(rse.endDate)}`;
    return `${dayOfWeek} ${hours}`
}



const recurringScheduleElementToShortDateString = (rse: RecurringScheduleElement) => {
    const dayOfWeek = dayOfWeekToShortString(rse.dayOfWeek);
    const hours = `${dateToHour(rse.startDate)} - ${dateToHour(rse.endDate)}`;
    return `${dayOfWeek} ${hours}`
}

export { recurringScheduleElementToLongDateString, recurringScheduleElementToShortDateString };