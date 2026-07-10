import React, { useState } from 'react';
import { X, Calendar, Clock, Users, MessageSquare } from 'lucide-react';
import API from '../api';
import { useAuth } from '../context/AuthContext';
import { useToast } from '../context/ToastContext';

const ReservationModal = ({ isOpen, onClose, restaurantId, restaurantName, onOpenAuth }) => {
    const { user } = useAuth();
    const { showToast } = useToast();
    const [date, setDate] = useState('');
    const [time, setTime] = useState('');
    const [partySize, setPartySize] = useState(2);
    const [specialRequests, setSpecialRequests] = useState('');
    const [isSubmitting, setIsSubmitting] = useState(false);

    if (!isOpen) return null;

    const handleSubmit = async (e) => {
        e.preventDefault();
        
        if (!user) {
            onClose();
            onOpenAuth();
            showToast('info', 'Please sign in to book a table.');
            return;
        }

        if (!date || !time) {
            showToast('error', 'Please select a date and time.');
            return;
        }

        setIsSubmitting(true);
        try {
            const payload = {
                restaurantId,
                reservationDate: date,
                reservationTime: time,
                partySize,
                specialRequests
            };
            await API.createReservation(payload);
            showToast('success', 'Table reserved successfully! 🎉');
            onClose();
        } catch (error) {
            showToast('error', error.message || 'Failed to book table.');
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <div className="modal-overlay" onClick={onClose}>
            <div className="modal" onClick={e => e.stopPropagation()}>
                <button className="modal-close" onClick={onClose}><X size={24} /></button>
                
                <div className="modal-header">
                    <h2>Book a Table</h2>
                    <p>at {restaurantName}</p>
                </div>

                <form onSubmit={handleSubmit} className="reservation-form">
                    <div className="form-row" style={{ display: 'flex', gap: '20px', marginBottom: '20px' }}>
                        <div className="form-group" style={{ flex: 1, marginBottom: 0 }}>
                            <label>Date</label>
                            <div className="input-with-icon">
                                <Calendar size={18} className="input-icon" />
                                <input 
                                    type="date" 
                                    required 
                                    min={new Date().toISOString().split('T')[0]}
                                    value={date}
                                    onChange={(e) => setDate(e.target.value)}
                                />
                            </div>
                        </div>

                        <div className="form-group" style={{ flex: 1, marginBottom: 0 }}>
                            <label>Time</label>
                            <div className="input-with-icon">
                                <Clock size={18} className="input-icon" />
                                <input 
                                    type="time" 
                                    required
                                    value={time}
                                    onChange={(e) => setTime(e.target.value)}
                                />
                            </div>
                        </div>
                    </div>

                    <div className="form-group">
                        <label>Party Size</label>
                        <div className="input-with-icon">
                            <Users size={18} className="input-icon" />
                            <select 
                                value={partySize} 
                                onChange={(e) => setPartySize(Number(e.target.value))}
                            >
                                {[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12].map(num => (
                                    <option key={num} value={num}>
                                        {num} {num === 1 ? 'Person' : 'People'}
                                    </option>
                                ))}
                            </select>
                        </div>
                    </div>

                    <div className="form-group">
                        <label>Special Requests (Optional)</label>
                        <div className="input-with-icon" style={{ alignItems: 'flex-start' }}>
                            <MessageSquare size={18} className="input-icon" style={{ top: '16px' }} />
                            <textarea 
                                rows="3"
                                placeholder="e.g., Anniversary, Dietary Restrictions, preferred seating..."
                                value={specialRequests}
                                onChange={(e) => setSpecialRequests(e.target.value)}
                                style={{
                                    width: '100%',
                                    background: 'var(--bg-surface-2)',
                                    border: '1px solid var(--border-color)',
                                    padding: '14px 16px 14px 48px',
                                    borderRadius: 'var(--radius-md)',
                                    color: 'var(--text-primary)',
                                    fontSize: '15px',
                                    fontFamily: 'var(--font-family)',
                                    resize: 'none'
                                }}
                            />
                        </div>
                    </div>

                    <button 
                        type="submit" 
                        className="btn-primary" 
                        style={{ width: '100%', marginTop: '20px' }}
                        disabled={isSubmitting}
                    >
                        {isSubmitting ? 'Confirming...' : 'Confirm Reservation'}
                    </button>
                </form>
            </div>
        </div>
    );
};

export default ReservationModal;
