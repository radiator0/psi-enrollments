import RecurringScheduleElement from '../dto/recurring-schedule-element';
import { WeekType } from '../../enumerations/week-type.model';
import { dayOfWeekToLongString, dayOfWeekToShortString } from './day-of-week-to-string-mapper';
import { weekTypeToLongString, weekTypeToShortString } from './week-type-to-string-mapper';
import { semesterPeriodToLongString, semesterPeriodToShortString } from './semester-period-to-string-mapper';
import { SemesterPeriod } from '../../enumerations/semester-period.model';

const dateToHour = (date: Date) => {
    return new Date(Date.parse(date.toString())).toLocaleTimeString().slice(0, -3);
}

const recurringScheduleElementToLongDateString = (rse: RecurringScheduleElement) => {
    const dayOfWeek = dayOfWeekToLongString(rse.dayOfWeek);
    const hours = `${dateToHour(rse.startDate)} - ${dateToHour(rse.endDate)}`;
    const weekType = rse.weekType !== WeekType.All ? ` ${weekTypeToLongString(rse.weekType)}` : '';
    const semesterPeriod = rse.semesterPeriod !== SemesterPeriod.Whole ? ` ${semesterPeriodToLongString(rse.semesterPeriod)}` : '';
    return `${dayOfWeek} ${hours}${weekType}${semesterPeriod}`;
}


const recurringScheduleElementToShortDateString = (rse: RecurringScheduleElement) => {
    const dayOfWeek = dayOfWeekToShortString(rse.dayOfWeek);
    const hours = `${dateToHour(rse.startDate)} - ${dateToHour(rse.endDate)}`;
    const weekType = rse.weekType !== WeekType.All ? ` ${weekTypeToShortString(rse.weekType)}` : '';
    const semesterPeriod = rse.semesterPeriod !== SemesterPeriod.Whole ? ` ${semesterPeriodToShortString(rse.semesterPeriod)}` : '';
    return `${dayOfWeek} ${hours}${weekType}${semesterPeriod}`;
}

export { recurringScheduleElementToLongDateString, recurringScheduleElementToShortDateString };