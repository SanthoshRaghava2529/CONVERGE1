import { useEffect, useMemo, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../services/api";

function defaultEventDate() {
  const date = new Date();
  date.setDate(date.getDate() + 2);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");
  return `${year}-${month}-${day}`;
}

function GroupConverge() {
  const navigate = useNavigate();
  const [mode, setMode] = useState("create");
  const [groupCode, setGroupCode] = useState("");
  const [createdGroup, setCreatedGroup] = useState(null);
  const [groups, setGroups] = useState([]);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const [listLoading, setListLoading] = useState(true);

  const [form, setForm] = useState({
    name: "Saturday Night",
    city: "Chennai",
    eventDate: defaultEventDate(),
    maxBudget: 1000,
  });

  const loadGroups = async () => {
    try {
      setListLoading(true);
      const response = await api.get("/groups");
      setGroups(response.data || []);
    } catch (err) {
      setError(err.response?.data?.message || "Could not load your groups.");
    } finally {
      setListLoading(false);
    }
  };

  useEffect(() => {
    loadGroups();
  }, []);

  const createGroup = async (e) => {
    e.preventDefault();
    setError("");
    setLoading(true);

    try {
      const response = await api.post("/groups", form);
      setCreatedGroup(response.data);
      await loadGroups();
    } catch (err) {
      setError(
        err.response?.data?.message ||
          "Could not create the group. Use today or a future date."
      );
    } finally {
      setLoading(false);
    }
  };

  const joinGroup = async (e) => {
    e.preventDefault();
    setError("");
    setLoading(true);

    try {
      await api.post("/groups/join", { groupCode });
      navigate(`/groups/${groupCode.trim().toUpperCase()}`);
    } catch (err) {
      setError(err.response?.data?.message || "Could not join the group.");
    } finally {
      setLoading(false);
    }
  };

  const hint = useMemo(
    () =>
      `Use a date that has seeded schedules, such as ${defaultEventDate()}.`,
    []
  );

  return (
    <main className="mx-auto max-w-5xl px-6 py-12">
      <div className="text-center">
        <p className="text-sm font-bold uppercase tracking-wider text-indigo-600">
          Group Converge
        </p>
        <h1 className="mt-2 text-4xl font-black text-slate-900">
          Find what everyone agrees on.
        </h1>
        <p className="mx-auto mt-4 max-w-2xl text-slate-500">
          Create a group, invite your friends and combine everyone's
          preferences into one decision.
        </p>
      </div>

      <div className="mx-auto mt-10 max-w-xl rounded-3xl border border-slate-200 bg-white p-8 shadow-lg">
        <div className="grid grid-cols-2 rounded-xl bg-slate-100 p-1">
          <button
            onClick={() => setMode("create")}
            className={`rounded-lg py-2.5 text-sm font-bold ${
              mode === "create"
                ? "bg-white text-slate-900 shadow-sm"
                : "text-slate-500"
            }`}
            type="button"
          >
            Create Group
          </button>
          <button
            onClick={() => setMode("join")}
            className={`rounded-lg py-2.5 text-sm font-bold ${
              mode === "join"
                ? "bg-white text-slate-900 shadow-sm"
                : "text-slate-500"
            }`}
            type="button"
          >
            Join Group
          </button>
        </div>

        {error && (
          <div className="mt-5 rounded-lg bg-red-50 p-3 text-sm text-red-600">
            {error}
          </div>
        )}

        {mode === "create" ? (
          <form onSubmit={createGroup} className="mt-7 space-y-4">
            <input
              value={form.name}
              onChange={(e) => setForm({ ...form, name: e.target.value })}
              placeholder="Group name"
              required
              className="w-full rounded-xl border border-slate-300 px-4 py-3"
            />
            <input
              value={form.city}
              onChange={(e) => setForm({ ...form, city: e.target.value })}
              placeholder="City"
              required
              className="w-full rounded-xl border border-slate-300 px-4 py-3"
            />
            <input
              type="date"
              value={form.eventDate}
              onChange={(e) => setForm({ ...form, eventDate: e.target.value })}
              className="w-full rounded-xl border border-slate-300 px-4 py-3"
              required
            />
            <p className="text-xs text-slate-400">{hint}</p>
            <input
              type="number"
              min={1}
              value={form.maxBudget}
              onChange={(e) =>
                setForm({ ...form, maxBudget: Number(e.target.value) })
              }
              placeholder="Budget"
              className="w-full rounded-xl border border-slate-300 px-4 py-3"
              required
            />
            <button
              disabled={loading}
              className="w-full rounded-xl bg-indigo-600 py-3 font-bold text-white hover:bg-indigo-700 disabled:opacity-50"
            >
              {loading ? "Creating..." : "Create Group"}
            </button>
          </form>
        ) : (
          <form onSubmit={joinGroup} className="mt-7 space-y-4">
            <input
              value={groupCode}
              onChange={(e) => setGroupCode(e.target.value.toUpperCase())}
              placeholder="Enter group code e.g. CNV-FE48"
              className="w-full rounded-xl border border-slate-300 px-4 py-3 uppercase"
              required
            />
            <button
              disabled={loading}
              className="w-full rounded-xl bg-slate-900 py-3 font-bold text-white hover:bg-slate-800 disabled:opacity-50"
            >
              {loading ? "Joining..." : "Join Group"}
            </button>
          </form>
        )}

        {createdGroup && (
          <div className="mt-7 rounded-2xl bg-indigo-50 p-6">
            <p className="text-sm font-semibold text-indigo-600">
              Group created successfully
            </p>
            <p className="mt-2 text-3xl font-black tracking-widest text-slate-900">
              {createdGroup.groupCode}
            </p>
            <p className="mt-2 text-sm text-slate-500">
              Share this code with your friends.
            </p>
            <div className="mt-5 space-y-2 text-sm text-slate-600">
              <p>📍 {createdGroup.city}</p>
              <p>📅 {createdGroup.eventDate}</p>
              <p>💰 ₹{createdGroup.maxBudget} budget</p>
            </div>
            <button
              onClick={() => navigate(`/groups/${createdGroup.groupCode}`)}
              className="mt-5 w-full rounded-xl bg-indigo-600 py-3 font-bold text-white"
            >
              Open group room
            </button>
          </div>
        )}
      </div>

      <section className="mt-12">
        <h2 className="text-2xl font-black">Your groups</h2>
        {listLoading && <p className="mt-4 text-slate-500">Loading groups...</p>}
        {!listLoading && groups.length === 0 && (
          <p className="mt-4 text-slate-500">You have not joined any groups yet.</p>
        )}
        <div className="mt-5 grid gap-4">
          {groups.map((group) => (
            <Link
              key={group.id}
              to={`/groups/${group.groupCode}`}
              className="rounded-2xl border border-slate-200 bg-white p-5 hover:border-indigo-300"
            >
              <div className="flex items-center justify-between gap-4">
                <div>
                  <p className="text-xs font-bold uppercase text-indigo-600">
                    {group.groupCode}
                  </p>
                  <h3 className="mt-1 font-black">{group.name}</h3>
                  <p className="text-sm text-slate-500">
                    {group.city} · {group.eventDate}
                  </p>
                </div>
                <span className="rounded-full bg-slate-100 px-3 py-1 text-xs font-bold">
                  {group.status}
                </span>
              </div>
            </Link>
          ))}
        </div>
      </section>
    </main>
  );
}

export default GroupConverge;
