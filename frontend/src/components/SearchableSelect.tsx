import { useEffect, useRef, useState } from "react";

export interface SearchableOption {
  value: string | number;
  label: string;
}

interface SearchableSelectProps {
  label: string;
  options: SearchableOption[];
  value: string | number;
  onChange: (value: string | number) => void;
  placeholder?: string;
}

function SearchableSelect({
  label,
  options,
  value,
  onChange,
  placeholder = "Search...",
}: SearchableSelectProps) {
  const wrapperRef = useRef<HTMLDivElement | null>(null);
  const [isOpen, setIsOpen] = useState(false);
  const [search, setSearch] = useState("");

  const showSearch = options.length > 5;

  const selectedOption = options.find((option) => option.value === value);

  const filteredOptions = options.filter((option) =>
    option.label.toLowerCase().includes(search.toLowerCase())
  );

  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (
        wrapperRef.current &&
        !wrapperRef.current.contains(event.target as Node)
      ) {
        setIsOpen(false);
        setSearch("");
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  const handleSelect = (optionValue: string | number) => {
    onChange(optionValue);
    setIsOpen(false);
    setSearch("");
  };

  return (
    <div className="form-group" ref={wrapperRef}>
      <label>{label}</label>

      <div className="searchable-select">
        <button
          type="button"
          className="searchable-select-trigger"
          onClick={() => setIsOpen((prev) => !prev)}
        >
          {selectedOption?.label || `Select ${label}`}
          <span className="searchable-select-arrow">▾</span>
        </button>

        {isOpen && (
          <div className="searchable-select-dropdown">
            {showSearch && (
              <input
                type="text"
                className="searchable-select-input"
                placeholder={placeholder}
                value={search}
                onChange={(e) => setSearch(e.target.value)}
                autoFocus
              />
            )}

            <div className="searchable-select-options">
              {filteredOptions.length > 0 ? (
                filteredOptions.map((option) => (
                  <button
                    key={String(option.value)}
                    type="button"
                    className={`searchable-select-option ${
                      option.value === value ? "active" : ""
                    }`}
                    onClick={() => handleSelect(option.value)}
                  >
                    {option.label}
                  </button>
                ))
              ) : (
                <div className="searchable-select-empty">No results found</div>
              )}
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default SearchableSelect;