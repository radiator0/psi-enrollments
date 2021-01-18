/* eslint-disable no-unused-labels */
/* eslint-disable no-labels */
/* eslint no-console: off */
const log = {
  info(message: any) {
    if (process.env.NODE_ENV !== 'production') {
      console.log(message);
    }
  },
  error(message: any) {
    if (process.env.NODE_ENV !== 'production') {
      console.error(message);
    }
  },
};

export default log;
