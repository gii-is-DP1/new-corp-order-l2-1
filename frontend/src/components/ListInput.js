import React, { useState } from 'react';

export default function ListInput({ onAddItem }) {
    const [newItem, setNewItem] = useState('');

    const handleAddItem = () => {
        if (newItem.trim() !== '') {
            onAddItem(newItem);
            setNewItem('');
        }
    };

    return (
        <div style={{ display: 'flex', marginBottom: '10px' }}>
            <input
                type="text"
                value={newItem}
                onChange={(e) => setNewItem(e.target.value)}
                placeholder="Escribe algo..."
                style={{
                    flex: 1,
                    padding: '8px',
                    border: '1px solid #ccc',
                    borderRadius: '4px',
                }}
            />
            <button
                onClick={handleAddItem}
                style={{
                    marginLeft: '8px',
                    padding: '8px',
                    backgroundColor: '#4caf50',
                    color: 'white',
                    border: 'none',
                    borderRadius: '4px',
                    cursor: 'pointer',
                }}
            >
                Agregar
            </button>
        </div>
    );
}
