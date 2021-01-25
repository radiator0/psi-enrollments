import RecurringScheduleElement from '../../../../shared/model/domain/dto/recurring-schedule-element';
import ScheduleData from '../../schedule-data';
import { DayOfWeek } from '../../../../shared/model/enumerations/day-of-week.model';

const dayOfWeekToNumber = {
    [`${DayOfWeek.Monday}`]: 0,
    [`${DayOfWeek.Tuesday}`]: 1,
    [`${DayOfWeek.Wednesday}`]: 2,
    [`${DayOfWeek.Thursday}`]: 3,
    [`${DayOfWeek.Friday}`]: 4,
    [`${DayOfWeek.Saturday}`]: 5,
    [`${DayOfWeek.Sunday}`]: 6,
}

const normalizeDate = (date : Date, dayOfWeek: DayOfWeek) => {
    const d = new Date(date);
    const day = d.getDay();
    const diff = d.getDate() - day + (day === 0 ? -6:1) + 7 + (day >=6 ? 7 : 0) + dayOfWeekToNumber[dayOfWeek];
    // eslint-disable-next-line @typescript-eslint/ban-ts-ignore
    // @ts-ignore
    return new Date(d.setDate(diff))
};

export default function mapScheduleElementToScheduleData(rse : RecurringScheduleElement) {
    return new ScheduleData(rse.id, normalizeDate(rse.startDate, rse.dayOfWeek), normalizeDate(rse.endDate, rse.dayOfWeek), rse.courseShortName || rse.courseName,
        rse.classType.toString(), rse.weekType, rse.semesterPeriod,
        rse.lecturerTitle, rse.lecturerFirstName, rse.lecturerSecondName, rse.lecturerLastName,
        rse.room, rse.building);
}