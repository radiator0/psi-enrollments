import Avatar from '@material-ui/core/Avatar/Avatar';
import React from 'react';
import CheckIcon from '@material-ui/icons/Check';
import ClearIcon from '@material-ui/icons/Clear';
import { makeStyles, Theme, createStyles } from '@material-ui/core';
import { red, green } from '@material-ui/core/colors';

const classes = makeStyles((theme: Theme) =>
  createStyles({
    red: {
      backgroundColor: red[900],
    },
    green: {
      backgroundColor: green[500],
    },
  }),
);

export const CheckedAvatar = () => (
  <Avatar className={classes().green}>
      <CheckIcon ></CheckIcon>
  </Avatar>
);

export const UnCheckedAvatar = () => (
  <Avatar className={classes().red}>
      <ClearIcon ></ClearIcon>
  </Avatar>
);


