import { useEffect, useMemo, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import api from "../services/api";
import { useAuth } from "../context/AuthContext";

function ExperienceDetails() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { isAuthenticated } = useAuth();

  const [experience, setExperience] = useState(null);
  const [schedules, setSchedules] = useState([]);
  const [selectedScheduleId, setSelectedScheduleId] = useState("");
  const [seatCount, setSeatCount] = useState(1);
  const [saved, setSaved] = useState(false);
  const [loading, setLoading] = useState(true);
  const [booking, setBooking] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    const load = async () => {
      try {
        setLoading(true);
        setError("");

        const [experienceRes, scheduleRes] = await Promise.all([
          api.get(`/experiences/${id}`),
          api.get(`/schedules/experience/${id}`),
        ]);

        setExperience(experienceRes.data);
        setSchedules(scheduleRes.data || []);

        if (scheduleRes.data?.length) {
          setSelectedScheduleId(String(scheduleRes.data[0].id));
        }

        if (isAuthenticated) {
          const statusRes = await api.get(`/saved/${id}/status`);
          setSaved(Boolean(statusRes.data));
        }
      } catch (err) {
        setError(
          err.response?.data?.message || "Could not load this experience."
        );
      } finally {
        setLoading(false);
      }
    };

    load();
  }, [id, isAuthenticated]);

  const selectedSchedule = useMemo(
    () => schedules.find((item) => String(item.id) === String(selectedScheduleId)),
    [schedules, selectedScheduleId]
  );

  const totalPrice = selectedSchedule
    ? Number(selectedSchedule.price) * Number(seatCount || 0)
    : 0;

  const toggleSave = async () => {
    if (!isAuthenticated) {
      navigate("/login", { state: { from: `/experience/${id}` } });
      return;
    }

    try {
      if (saved) {
        await api.delete(`/saved/${id}`);
        setSaved(false);
      } else {
        await api.post(`/saved/${id}`);
        setSaved(true);
      }
    } catch (err) {
      setError(err.response?.data?.message || "Could not update saved list.");
    }
  };

  const book = async () => {
    if (!isAuthenticated) {
      navigate("/login", { state: { from: `/experience/${id}` } });
      return;
    }

    if (!selectedSchedule) {
      setError("Please select a schedule.");
      return;
    }

    setBooking(true);
    setError("");

    try {
      const response = await api.post(
        `/bookings?scheduleId=${selectedSchedule.id}&seatCount=${seatCount}`
      );
      navigate(`/bookings/${response.data.bookingReference}`);
    } catch (err) {
      setError(err.response?.data?.message || "Booking failed.");
    } finally {
      setBooking(false);
    }
  };

  if (loading) {
    return (
      <main className="mx-auto max-w-5xl px-6 py-16 text-center text-slate-500">
        Loading experience...
      </main>
    );
  }

  if (error && !experience) {
    return (
      <main className="mx-auto max-w-5xl px-6 py-16">
        <div className="rounded-2xl border border-red-200 bg-red-50 p-6 text-red-700">
          {error}
        </div>
      </main>
    );
  }

  return (
    <main className="mx-auto max-w-5xl px-6 py-10">
      <Link to="/explore" className="text-sm font-semibold text-indigo-600">
        ← Back to explore
      </Link>

      <div className="mt-6 overflow-hidden rounded-3xl border border-slate-200 bg-white shadow-sm">
        {experience.imageUrl && (
          <img
            src={experience.imageUrl}
            alt={experience.title}
            className="h-72 w-full object-cover"
          />
        )}

        <div className="p-8">
          <div className="flex flex-wrap items-center gap-3">
            <span className="rounded-full bg-indigo-50 px-3 py-1 text-sm font-bold text-indigo-600">
              {experience.category}
            </span>
            <span className="rounded-full bg-slate-100 px-3 py-1 text-sm font-semibold text-slate-600">
              {experience.genre}
            </span>
            <span className="rounded-full bg-slate-100 px-3 py-1 text-sm font-semibold text-slate-600">
              {experience.language}
            </span>
          </div>

          <div className="mt-4 flex flex-wrap items-start justify-between gap-4">
            <div>
              <h1 className="text-4xl font-black text-slate-900">
                {experience.title}
              </h1>
              <p className="mt-3 text-slate-500">{experience.description}</p>
            </div>
            <button
              onClick={toggleSave}
              className="rounded-xl border border-slate-200 px-4 py-2 text-sm font-semibold"
            >
              {saved ? "Saved" : "Save"}
            </button>
          </div>

          <div className="mt-6 grid gap-4 sm:grid-cols-3">
            <div className="rounded-xl bg-slate-50 p-4">
              <p className="text-xs font-bold uppercase text-slate-400">Venue</p>
              <p className="mt-1 font-semibold">{experience.venueName}</p>
            </div>
            <div className="rounded-xl bg-slate-50 p-4">
              <p className="text-xs font-bold uppercase text-slate-400">City</p>
              <p className="mt-1 font-semibold">{experience.city}</p>
            </div>
            <div className="rounded-xl bg-slate-50 p-4">
              <p className="text-xs font-bold uppercase text-slate-400">
                Duration
              </p>
              <p className="mt-1 font-semibold">
                {experience.durationMinutes} minutes
              </p>
            </div>
          </div>
        </div>
      </div>

      {error && (
        <div className="mt-6 rounded-xl bg-red-50 p-4 text-red-600">{error}</div>
      )}

      <section className="mt-8 rounded-3xl border border-slate-200 bg-white p-8">
        <h2 className="text-2xl font-black">Select a schedule</h2>

        {schedules.length === 0 ? (
          <p className="mt-4 text-slate-500">
            No upcoming schedules are available for this experience.
          </p>
        ) : (
          <div className="mt-5 grid gap-3">
            {schedules.map((schedule) => (
              <label
                key={schedule.id}
                className={`flex cursor-pointer items-center justify-between rounded-2xl border px-4 py-4 ${
                  String(selectedScheduleId) === String(schedule.id)
                    ? "border-indigo-500 bg-indigo-50"
                    : "border-slate-200"
                }`}
              >
                <div>
                  <p className="font-semibold">
                    {schedule.scheduleDate} · {schedule.startTime} -{" "}
                    {schedule.endTime}
                  </p>
                  <p className="text-sm text-slate-500">
                    {schedule.availableSeats} seats left · ₹{schedule.price}
                  </p>
                </div>
                <input
                  type="radio"
                  name="schedule"
                  checked={String(selectedScheduleId) === String(schedule.id)}
                  onChange={() => setSelectedScheduleId(String(schedule.id))}
                />
              </label>
            ))}
          </div>
        )}

        <div className="mt-6 flex flex-wrap items-end gap-4">
          <label className="text-sm font-semibold text-slate-600">
            Quantity
            <input
              type="number"
              min={1}
              max={selectedSchedule?.availableSeats || 10}
              value={seatCount}
              onChange={(e) => setSeatCount(Number(e.target.value))}
              className="mt-2 block w-28 rounded-xl border border-slate-300 px-4 py-3"
            />
          </label>
          <div>
            <p className="text-sm text-slate-500">Total</p>
            <p className="text-2xl font-black">₹{totalPrice.toFixed(2)}</p>
          </div>
          <button
            onClick={book}
            disabled={booking || !selectedSchedule}
            className="rounded-xl bg-indigo-600 px-6 py-3 font-bold text-white hover:bg-indigo-700 disabled:opacity-50"
          >
            {booking ? "Booking..." : "Confirm booking"}
          </button>
        </div>
      </section>
    </main>
  );
}

export default ExperienceDetails;
