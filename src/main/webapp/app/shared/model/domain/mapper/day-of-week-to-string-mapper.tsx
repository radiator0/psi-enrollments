// @ts-nocheck
import DayOfWeek from "../enum/day-of-week"

const dayOfWeekToLongString = (dow: DayOfWeek) => {
    switch (dow) {
        case 'Monday':
            return 'Monday';
        case 'Tuesday':
            return 'Tuesday';
        case 'Wednesday':
            return 'Wednesday';
        case 'Thursday':
            return 'Thursday';
        case 'Friday':
            return 'Friday';
        case 'Saturday':
            return 'Saturday';
        case 'Sunday':
            return 'Sunday';
        default:
            return '';
    }
}

const dayOfWeekToShortString = (dow: DayOfWeek) => {
    switch (dow) {
        case 'Monday':
            return 'Mon';
        case 'Tuesday':
            return 'Tue';
        case 'Wednesday':
            return 'Wed';
        case 'Thursday':
            return 'Thu';
        case 'Friday':
            return 'Fri';
        case 'Saturday':
            return 'Sat';
        case 'Sunday':
            return 'Sun';
        default:
            return '';
    }
}

export { dayOfWeekToLongString, dayOfWeekToShortString }