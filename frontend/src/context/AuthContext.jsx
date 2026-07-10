/* eslint-disable react-refresh/only-export-components */
import React, { createContext, useContext, useState, useEffect } from 'react';
import API from '../api';
import { useToast } from './ToastContext';

const AuthContext = createContext();

export const useAuth = () => useContext(AuthContext);

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);
    const { showToast } = useToast();

    useEffect(() => {
        checkSession();
    }, []);

    const checkSession = async () => {
        try {
            const res = await API.me();
            if (res.success) {
                setUser(res.data);
            }
        } catch (error) {
            // Not logged in or session expired
            setUser(null);
        } finally {
            setLoading(false);
        }
    };

    const login = async (username, password) => {
        try {
            const res = await API.login({ username, password });
            if (res.success) {
                setUser(res.data);
                showToast('success', `Welcome back, ${res.data.username}!`);
                return true;
            }
        } catch (error) {
            const msg = error.message || 'Login failed. Please try again.';
            throw new Error(msg);
        }
    };

    const register = async (username, password, email, address) => {
        try {
            const res = await API.register({ username, password, email, address });
            if (res.success) {
                // Auto login after successful registration
                return await login(username, password);
            }
        } catch (error) {
            const msg = error.message || 'Registration failed. Please try again.';
            throw new Error(msg);
        }
    };

    const logout = async () => {
        try {
            await API.logout();
        } catch (error) {
            console.error('Logout error:', error);
        } finally {
            setUser(null);
            showToast('info', 'You have been signed out.');
        }
    };

    const value = {
        user,
        loading,
        login,
        register,
        logout
    };

    return (
        <AuthContext.Provider value={value}>
            {!loading && children}
        </AuthContext.Provider>
    );
};
