import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ILecturer, defaultValue } from 'app/shared/model/lecturer.model';

export const ACTION_TYPES = {
  FETCH_LECTURER_LIST: 'lecturer/FETCH_LECTURER_LIST',
  FETCH_LECTURER: 'lecturer/FETCH_LECTURER',
  CREATE_LECTURER: 'lecturer/CREATE_LECTURER',
  UPDATE_LECTURER: 'lecturer/UPDATE_LECTURER',
  DELETE_LECTURER: 'lecturer/DELETE_LECTURER',
  RESET: 'lecturer/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ILecturer>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type LecturerState = Readonly<typeof initialState>;

// Reducer

export default (state: LecturerState = initialState, action): LecturerState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_LECTURER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_LECTURER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_LECTURER):
    case REQUEST(ACTION_TYPES.UPDATE_LECTURER):
    case REQUEST(ACTION_TYPES.DELETE_LECTURER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_LECTURER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_LECTURER):
    case FAILURE(ACTION_TYPES.CREATE_LECTURER):
    case FAILURE(ACTION_TYPES.UPDATE_LECTURER):
    case FAILURE(ACTION_TYPES.DELETE_LECTURER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_LECTURER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_LECTURER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_LECTURER):
    case SUCCESS(ACTION_TYPES.UPDATE_LECTURER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_LECTURER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/lecturers';

// Actions

export const getEntities: ICrudGetAllAction<ILecturer> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_LECTURER_LIST,
  payload: axios.get<ILecturer>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ILecturer> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_LECTURER,
    payload: axios.get<ILecturer>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ILecturer> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_LECTURER,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ILecturer> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_LECTURER,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ILecturer> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_LECTURER,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
