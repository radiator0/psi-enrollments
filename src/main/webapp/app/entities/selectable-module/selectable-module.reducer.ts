import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISelectableModule, defaultValue } from 'app/shared/model/selectable-module.model';

export const ACTION_TYPES = {
  FETCH_SELECTABLEMODULE_LIST: 'selectableModule/FETCH_SELECTABLEMODULE_LIST',
  FETCH_SELECTABLEMODULE: 'selectableModule/FETCH_SELECTABLEMODULE',
  CREATE_SELECTABLEMODULE: 'selectableModule/CREATE_SELECTABLEMODULE',
  UPDATE_SELECTABLEMODULE: 'selectableModule/UPDATE_SELECTABLEMODULE',
  DELETE_SELECTABLEMODULE: 'selectableModule/DELETE_SELECTABLEMODULE',
  RESET: 'selectableModule/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISelectableModule>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type SelectableModuleState = Readonly<typeof initialState>;

// Reducer

export default (state: SelectableModuleState = initialState, action): SelectableModuleState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SELECTABLEMODULE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SELECTABLEMODULE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_SELECTABLEMODULE):
    case REQUEST(ACTION_TYPES.UPDATE_SELECTABLEMODULE):
    case REQUEST(ACTION_TYPES.DELETE_SELECTABLEMODULE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_SELECTABLEMODULE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SELECTABLEMODULE):
    case FAILURE(ACTION_TYPES.CREATE_SELECTABLEMODULE):
    case FAILURE(ACTION_TYPES.UPDATE_SELECTABLEMODULE):
    case FAILURE(ACTION_TYPES.DELETE_SELECTABLEMODULE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_SELECTABLEMODULE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_SELECTABLEMODULE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_SELECTABLEMODULE):
    case SUCCESS(ACTION_TYPES.UPDATE_SELECTABLEMODULE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_SELECTABLEMODULE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/selectable-modules';

// Actions

export const getEntities: ICrudGetAllAction<ISelectableModule> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SELECTABLEMODULE_LIST,
  payload: axios.get<ISelectableModule>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ISelectableModule> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SELECTABLEMODULE,
    payload: axios.get<ISelectableModule>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ISelectableModule> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SELECTABLEMODULE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISelectableModule> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SELECTABLEMODULE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISelectableModule> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SELECTABLEMODULE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
