import { DayOfWeek } from '../../enumerations/day-of-week.model'
import { translate } from 'react-jhipster';

const dayOfWeekToLongString = (dow: DayOfWeek) => {
    return translate(`enrollmentsApp.DayOfWeek.${dow}`);
}

const dayOfWeekToShortString = (dow: DayOfWeek) => {
    return translate(`enrollmentsApp.ShortDayOfWeek.${dow}`);
}

export { dayOfWeekToLongString, dayOfWeekToShortString }